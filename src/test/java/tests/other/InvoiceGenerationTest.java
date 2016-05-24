package tests.other;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;

import javax.inject.Inject;

import main.core.calculation.Calculator;
import main.core.calculation.InvoiceGenerator;
import main.core.exception.GenerationException;
import main.domain.Car;
import main.domain.Rate;
import main.domain.simulation.CarTracker;
import main.domain.simulation.Position;
import main.domain.simulation.TrackingPeriod;
import main.service.CarService;
import main.service.RateService;
import tests.TestHelper;

/**
 * @author Sam
 */
@RunWith(Arquillian.class)
public class InvoiceGenerationTest {

    private static final Long TRACKER_ID = 100L;

    @Deployment
    public static Archive<?> createDeployment() {
        return TestHelper.createDeployment();
    }

    @Inject
    private InvoiceGenerator invoiceGenerator;

    @Inject
    private CarService carService;
    @Inject
    private RateService rateService;

    @Before
    public void before() {
        for (Car car : carService.getAll()) {
            carService.remove(car);
        }

        for (Rate rate : rateService.getAll()) {
            rateService.remove(rate);
        }
    }

    @Test
    public void testGeneration() throws GenerationException {
        CarTracker tracker = buildCarTracker();
       buildCar();

        invoiceGenerator.generateInvoice(tracker);

        LocalDate localDate = LocalDate.now();
        String fileName = "Invoice"
                + tracker.getId()
                + "-"
                + localDate.getMonth().name()
                + localDate.getYear()
                + ".pdf";
        File file = new File(fileName);

        Assert.assertTrue(file.exists());
    }

    @Test
    public void testPriceCalculation() throws GenerationException {
        CarTracker tracker = buildCarTracker();
        Car car = buildCar();

        Assert.assertEquals("Wrong price calculated"
                , new BigDecimal(140.765 * 2.0).setScale(1, BigDecimal.ROUND_HALF_UP)
                , invoiceGenerator.calculatePrice(tracker, car).setScale(1, BigDecimal.ROUND_HALF_UP));
    }

    @Test
    public void testCalculation() {
        CarTracker tracker = buildCarTracker();

        Assert.assertEquals("Wrong distance calculated", 140.765, Calculator.calcuateTotalDistance(tracker.getCurrentTrackingPeriod()), 0.1);
    }

    private Car buildCar() {
        Rate rate = new Rate();
        rate.setValue(new BigDecimal(2.0));

        rateService.create(rate);

        Car car = new Car();
        car.setCartrackerId(TRACKER_ID);
        car.setRate(rate);

        carService.create(car);

        return car;
    }

    private CarTracker buildCarTracker() {
        CarTracker tracker = new CarTracker();
        tracker.setId(TRACKER_ID);
        tracker.startTrackingPeriod();

        Position pos1 = new Position(38.898556, -77.037852);
        Position pos2 = new Position(39.897147, -78.043934);

        TrackingPeriod period = tracker.getCurrentTrackingPeriod();
        period.addPosition(pos1);
        period.addPosition(pos2);

        return tracker;
    }
}
