package main.core.calculation;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.inject.Named;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import main.core.communcation.Communicator;
import main.core.exception.GenerationException;
import main.core.jms.JMSInit;
import main.core.jms.JMSProducer;
import main.core.pdf.ITemplate;
import main.core.pdf.MonthlyInvoiceTemplate;
import main.core.pdf.PdfGenerator;
import main.domain.Car;
import main.domain.Invoice;
import main.domain.Rate;
import main.domain.Region;
import main.domain.enums.PaymentStatus;
import main.domain.foreign.InvoiceWrapper;
import main.domain.simulation.CarTracker;
import main.domain.simulation.TrackingPeriod;
import main.service.CarService;
import main.service.InvoiceService;
import main.service.RegionService;

/**
 * @author Sam
 */
@Named
public class InvoiceGenerator implements Serializable {

    @Inject
    private CarService carService;
    @Inject
    private InvoiceService invoiceService;
    @Inject
    private RegionService regionService;
    @Inject
    private JMSInit jmsInit;

    /**
     * Retrieve all the @{code Cartracker} from the MovementSystem
     * and generate a invoice as a PDF file containing the price a @{code Driver} has to pay
     * based on the amount of kilometers the @{code Driver} has driver in the current month.
     */
    public void generate() {
        LocalDate lastMonth = LocalDate.now().minusMonths(1);
        List<Long> tempids;
        HashMap<Long, String> idCountryMap = carService.getCountriesForForeignCartrackers();

        try {
            tempids = Communicator.getAllCartrackerIds();
            for (Long id : tempids) {
                if (id > 0L) {
                    idCountryMap.put(id, "");
                }
            }

        } catch (IOException e) {
            Logger.getLogger(Communicator.class.getName()).log(Level.SEVERE, null, e);
        }

        for (Map.Entry<Long, String> entry : idCountryMap.entrySet()) {
            try {
                Long trackerId = entry.getKey();
                String country = entry.getValue();
                List<TrackingPeriod> periods = Communicator.getTrackingPeriodsByMonth(trackerId, lastMonth.getMonthValue(), lastMonth.getYear());

                if (periods.size() > 0) {
                    CarTracker tracker = new CarTracker();
                    tracker.setId(trackerId);
                    tracker.setTrackingPeriods(periods);

                    Invoice invoice = generateInvoice(tracker);

                    if (!country.isEmpty()) {
                        JMSProducer jmsProducer = jmsInit.getProducerByExchangeName("portugal_foreign_invoice_exchange");
                        if (jmsProducer != null) {
                            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
                            jmsProducer.sendMessage(gson.toJson(new InvoiceWrapper(invoice, country)));
                        }
                    }
                }

            } catch (GenerationException | IOException ex) {
                Logger.getLogger(InvoiceGenerator.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Generate an invoice for a @{code CarTracker} for the current month.
     * The invoice is represented as a PDF file.
     *
     * @param tracker to generate the invoice for.
     */
    public Invoice generateInvoice(CarTracker tracker) throws GenerationException {
        Car car = carService.getCarByCartrackerId(tracker.getId());
        if (car == null) {
            throw new GenerationException("Car was not found and is null");
        }

        TreeMap<Double, BigDecimal> distanceAndPrice = calculatePrice(tracker.getTrackingPeriods(), car.getRate());

        LocalDate localDate = LocalDate.now();
        String fileName = "Invoice"
                + tracker.getId()
                + "-"
                + localDate.getMonth().name()
                + localDate.getYear();
        File invoiceFile = new File(fileName);

        Instant instant = localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
        Date month = Date.from(instant);

        Invoice invoice = new Invoice();
        invoice.setFilePath(invoiceFile.getAbsolutePath());
        invoice.setOwnership(car.getCurrentOwnership());
        invoice.setPaymentStatus(PaymentStatus.OPEN);
        invoice.setPeriod(month);
        invoice.setTotalAmount(distanceAndPrice.firstEntry().getValue());

        invoiceService.create(invoice);

        ITemplate template = new MonthlyInvoiceTemplate(invoice, distanceAndPrice.firstKey());
        PdfGenerator.generate(invoiceFile.getName(), template);
        return invoice;
    }

    /**
     * Calculate the price a @{code Driver} has to pay for a @{code Car} he/she has driven
     * in the current month based on data in the @{code CarTracker} of the @{code Car}.
     *
     * @param trackingPeriods to get the data from.
     * @return a @{code BigDecimal} representing the total price that has to be paid for the current month.
     */
    public TreeMap<Double, BigDecimal> calculatePrice(List<TrackingPeriod> trackingPeriods, Rate rate) {
        List<Region> regions = regionService.getAll();
        Map<Region, Double> distances = new HashMap<>();

        for (TrackingPeriod period : trackingPeriods) {
            Map<Region, Double> distancePerPeriod = Calculator.calculateTotalDistance(period.getPositions(), regions);

            for (Map.Entry<Region, Double> entry : distancePerPeriod.entrySet()) {
                if (distances.containsKey(entry.getKey())) {
                    double distance = distances.get(entry.getKey()) + entry.getValue();
                    distances.put(entry.getKey(), distance);
                } else {
                    distances.putAll(distancePerPeriod);
                }
            }
        }

        double totalDistance = 0.0;
        BigDecimal roadTax = BigDecimal.ZERO;
        for (Map.Entry<Region, Double> entry : distances.entrySet()) {
            Region region = entry.getKey();
            Double distance = entry.getValue();

            totalDistance += distance;

            roadTax = roadTax.add(region.getRoadTaxPerKm().multiply(new BigDecimal(distance)));
        }

        BigDecimal efficiencyTax = rate.getValue().multiply(BigDecimal.valueOf(totalDistance));
        TreeMap<Double, BigDecimal> distanceWithPrice = new TreeMap<>();
        distanceWithPrice.put(totalDistance, efficiencyTax.add(roadTax));
        return distanceWithPrice;
    }
}