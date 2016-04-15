package main.core.calculation;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.inject.Named;

import main.core.communcation.Communicator;
import main.core.pdf.ITemplate;
import main.core.pdf.MonthlyInvoiceTemplate;
import main.core.pdf.PdfGenerator;
import main.domain.Car;
import main.domain.simulation.CarTracker;
import main.domain.simulation.TrackingPeriod;
import main.service.CarService;

/**
 * @author Sam
 */
@Named
public class InvoiceGenerator {

    @Inject
    private CarService carService;

    /**
     * Retrieve all the @{code Cartracker} from the MovementSystem
     * and generate a invoice as a PDF file containing the price a @{code Driver} has to pay
     * based on the amount of kilometers the @{code Driver} has driver in the current month.
     */
    public void generate() {
        LocalDate now = LocalDate.now();

        try {
            for (Long trackerId : Communicator.getAllCartrackerIds()) {
                List<TrackingPeriod> periods = Communicator.getTrackingPeriodsByMonth(trackerId, now.getMonthValue(), now.getYear());


                //generateInvoice(tracker);
            }
        } catch (IOException e) {
            Logger.getLogger(Communicator.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /**
     * Generate an invoice for a @{code CarTracker} for the current month.
     * The invoice is represented as a PDF file.
     *
     * @param tracker to generate the invoice for.
     */
    public void generateInvoice(CarTracker tracker) {
        BigDecimal totalPrice = calculatePrice(tracker);

        LocalDate localDate = LocalDate.now();
        String fileName = "Invoice"
                + tracker.getId()
                + "-"
                + localDate.getMonth().name()
                + localDate.getYear();
        File invoiceFile = new File(fileName);

        ITemplate template = new MonthlyInvoiceTemplate(tracker, totalPrice);
        PdfGenerator.generate(invoiceFile.getName(), template);
    }

    /**
     * Calculate the price a @{code Driver} has to pay for a @{code Car} he/she has driven
     * in the current month based on data in the @{code CarTracker} of the @{code Car}.
     *
     * @param tracker to get the data from.
     * @return a @{code BigDecimal} representing the total price that has to be paid for the current month.
     */
    public BigDecimal calculatePrice(CarTracker tracker) {
        Car car = carService.getCarByCartrackerId(tracker.getId());

        double totalDistance = 0.0;
        for (TrackingPeriod period : tracker.getTrackingPeriods()) {
            totalDistance += Calculator.calcuateTotalDistance(period);
        }

        return car.getRate().getValue().multiply(new BigDecimal(totalDistance));
    }
}
