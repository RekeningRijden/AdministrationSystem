package tests.other;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

import javax.inject.Inject;

import main.core.calculation.Calculator;
import main.core.calculation.InvoiceGenerator;
import main.core.exception.GenerationException;
import main.domain.Address;
import main.domain.Car;
import main.domain.Driver;
import main.domain.Invoice;
import main.domain.Ownership;
import main.domain.Rate;
import main.domain.Region;
import main.domain.simulation.CarTracker;
import main.domain.simulation.Position;
import main.domain.simulation.TrackingPeriod;
import main.service.CarService;
import main.service.DriverService;
import main.service.InvoiceService;
import main.service.OwnershipService;
import main.service.RateService;
import main.service.RegionService;
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
    private DriverService driverService;
    @Inject
    private InvoiceService invoiceService;
    @Inject
    private OwnershipService ownershipService;
    @Inject
    private RateService rateService;
    @Inject
    private RegionService regionService;

    //@Before
    public void before() {
        //for(Ownership ownership : )

        for (Invoice invoice : invoiceService.getAll()) {
            invoice.setOwnership(null);
            invoiceService.update(invoice);
        }

        for (Ownership ownership : ownershipService.getAll()) {
            ownershipService.remove(ownership);
        }

        for (Car car : carService.getAll()) {
            carService.remove(car);
        }

        for (Rate rate : rateService.getAll()) {
            rateService.remove(rate);
        }
    }

    //@Test
    public void testGeneration() throws GenerationException {
        CarTracker tracker = createCarTracker();
        createCar();
        createRegions();

        invoiceGenerator.generateInvoice(tracker);

        LocalDate localDate = LocalDate.now();
        String fileName = "Invoice"
                + tracker.getId()
                + "-"
                + localDate.getMonth().name()
                + localDate.getYear();
        File file = new File("C:\\School\\" + fileName + ".pdf");

        Assert.assertTrue(file.exists());
    }

    @Test
    public void testPriceCalculation() throws GenerationException {
        CarTracker tracker = createCarTracker();
        Car car = createCar();
        createRegions();

        Assert.assertEquals("Wrong price calculated"
                , new BigDecimal(109.3 * 2.0).add(new BigDecimal(109.3 * 0.3)).setScale(1, BigDecimal.ROUND_HALF_UP)
                , invoiceGenerator.calculatePrice(tracker.getTrackingPeriods(), car.getRate()).setScale(1, BigDecimal.ROUND_HALF_UP));
    }

    @Test
    public void testCalculation() {
        CarTracker tracker = createCarTracker();

        Map<Region, Double> result =  Calculator.calculateTotalDistance(tracker.getCurrentTrackingPeriod().getPositions(), regionService.getAll());
        double totalDistance = 0.0;
        for (Map.Entry<Region, Double> entry : result.entrySet()) {
            totalDistance += entry.getValue();
        }

        Assert.assertEquals("Wrong distance calculated", 109.3,  totalDistance, 0.1);
    }

    private Car createCar() {
        Rate rate = new Rate();
        rate.setName("A");
        rate.setValue(new BigDecimal(2.0));

        rateService.create(rate);

        Car car = new Car();
        car.setCartrackerId(TRACKER_ID);
        car.setRate(rate);

        Ownership ownership = new Ownership();
        ownership.setCar(car);

        car.setCurrentOwnership(ownership);

        carService.create(car);

        Address address = new Address();
        address.setStreet("Henkstraat");
        address.setStreetNr("12");
        address.setZipCode("8442HD");
        address.setCity("Rotterdam");
        address.setCountry("Netherlands");

        Driver driver = new Driver();
        driver.setFirstName("Sam");
        driver.setLastName("Jansen");
        driver.setEmail("samjansen16@gmail.com");
        driver.setAddress(address);
        driver.getOwnerships().add(ownership);

        driverService.create(driver);

        car.getCurrentOwnership().setDriver(driver);
        car = carService.update(car);

        return car;
    }

    private CarTracker createCarTracker() {
        CarTracker tracker = new CarTracker();
        tracker.setId(TRACKER_ID);
        tracker.startTrackingPeriod();

        Position pos1 = new Position(38.898556, -77.037852);
        Position pos2 = new Position(39.497147, -78.043934);

        TrackingPeriod period = tracker.getCurrentTrackingPeriod();
        period.addPosition(pos1);
        period.addPosition(pos2);

        return tracker;
    }

    private void createRegions() {
        regionService.create(new Region("NORTE", 40.5, new BigDecimal(0.2)));
        regionService.create(new Region("CENTRO", 39.5, new BigDecimal(0.25)));
        regionService.create(new Region("ALENTEJO", 38.0, new BigDecimal(0.3)));
        regionService.create(new Region("ALGARVE", 37.2, new BigDecimal(0.35)));
    }
}
