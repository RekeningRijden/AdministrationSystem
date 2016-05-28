package main.core.calculation;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.inject.Named;

import main.core.communcation.Communicator;
import main.core.exception.GenerationException;
import main.core.pdf.ITemplate;
import main.core.pdf.MonthlyInvoiceTemplate;
import main.core.pdf.PdfGenerator;
import main.domain.Car;
import main.domain.Invoice;
import main.domain.Region;
import main.domain.enums.PaymentStatus;
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

    /**
     * Retrieve all the @{code Cartracker} from the MovementSystem
     * and generate a invoice as a PDF file containing the price a @{code Driver} has to pay
     * based on the amount of kilometers the @{code Driver} has driver in the current month.
     */
    public void generate() {
        LocalDate now = LocalDate.now();
        List<Long> ids = null;

        try {
            ids = Communicator.getAllCartrackerIds();
        } catch (IOException e) {
            Logger.getLogger(Communicator.class.getName()).log(Level.SEVERE, null, e);
        }

        if (ids != null) {
            for (Long trackerId : ids) {
                try {
                    List<TrackingPeriod> periods = Communicator.getTrackingPeriodsByMonth(trackerId, now.getMonthValue(), now.getYear());

                    CarTracker tracker = new CarTracker();
                    tracker.setId(trackerId);
                    tracker.setTrackingPeriods(periods);

                    generateInvoice(tracker);
                } catch (GenerationException | IOException ex) {
                    Logger.getLogger(InvoiceGenerator.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    /**
     * Generate an invoice for a @{code CarTracker} for the current month.
     * The invoice is represented as a PDF file.
     *
     * @param tracker to generate the invoice for.
     */
    public void generateInvoice(CarTracker tracker) throws GenerationException {
        Car car = carService.getCarByCartrackerId(tracker.getId());
        if (car == null) {
            throw new GenerationException("Car was not found and is null");
        }

        BigDecimal totalPrice = calculatePrice(tracker, car);

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
        invoice.setTotalAmount(totalPrice);

        invoiceService.create(invoice);

        ITemplate template = new MonthlyInvoiceTemplate(invoice, 111.0);
        PdfGenerator.generate(invoiceFile.getName(), template);
    }

    /**
     * Calculate the price a @{code Driver} has to pay for a @{code Car} he/she has driven
     * in the current month based on data in the @{code CarTracker} of the @{code Car}.
     *
     * @param tracker to get the data from.
     * @return a @{code BigDecimal} representing the total price that has to be paid for the current month.
     */
    public BigDecimal calculatePrice(CarTracker tracker, Car car) {
        List<Region> regions = regionService.getAll();
        Map<Region, Double> distances = new HashMap<>();

        for (TrackingPeriod period : tracker.getTrackingPeriods()) {
            distances.putAll(Calculator.calculateTotalDistance(period, regions));
        }

        double totalDistance = 0.0;
        BigDecimal roadTax = BigDecimal.ZERO;
        for (Map.Entry<Region, Double> entry : distances.entrySet()) {
            Region region = entry.getKey();
            Double distance = entry.getValue();

            totalDistance += distance;

            roadTax = roadTax.add(region.getRoadTaxPerKm().multiply(new BigDecimal(distance)));
        }

        BigDecimal efficiencyTax = car.getRate().getValue().multiply(new BigDecimal(totalDistance));
        return efficiencyTax.add(roadTax);
    }
}
