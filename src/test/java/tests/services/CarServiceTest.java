package tests.services;

import junit.framework.Assert;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;

import main.core.exception.DriverAssignmentException;
import main.domain.Car;
import main.domain.Driver;
import main.service.CarService;
import tests.TestHelper;

/**
 * @author Sam
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(Arquillian.class)
public class CarServiceTest {

    @Deployment
    public static Archive<?> createDeployment() {
        return TestHelper.createDeployment();
    }

    @Inject
    private CarService carService;

    @Test
    public void test1SetupWithoutCar() {
        Car car = carService.findOrSetup(null);

        //Assert.assertNull("A car has been found but there should be none in the database", car.getId());
        //Assert.assertNotNull("Car doesn't have an ownership", car.getCurrentOwnership());
        //Assert.assertEquals("The car's ownership car is not the same as the car", car, car.getCurrentOwnership().getCar());
    }

    @Test
    public void test2CreateOrUpdateWithoutDriver() {
        Car car = carService.findOrSetup(null);

        boolean success = false;
        try {
            carService.createOrUpdate(car);
        } catch (DriverAssignmentException e) {
            success = true;
        } catch (Exception e) {
        }

        if (!success) {
            //Assert.fail("No exception thrown when driver is empty");
        }
    }

    @Test
    public void test3CreateOrUpdateWithDriver() {
        Driver driver = new Driver();
        driver.setFirstName("DriverOne");

        Car car = carService.findOrSetup(null);
        car.getCurrentOwnership().setDriver(driver);

        try {
            //car = carService.createOrUpdate(car);
        } catch (Exception e) {
            Logger.getLogger(CarServiceTest.class.getName()).log(Level.SEVERE, null, e);
        }

        //Assert.assertTrue("Car has not been created", carService.hasBeenPersisted(car));
        //Assert.assertEquals("Not the correct driver has been added to the cars current ownership", driver, car.getCurrentOwnership().getDriver());

        car.setLicencePlate("11-AA-11");

        try {
            //car = carService.createOrUpdate(car);
        } catch (Exception e) {
            Logger.getLogger(CarServiceTest.class.getName()).log(Level.SEVERE, null, e);
        }

        //Assert.assertEquals("There are too many cars in the database", 1, carService.getAll().size());
        //Assert.assertEquals("Car has not been successfully updated", "11-AA-11", car.getLicencePlate());
    }

    @Test
    public void test4SetupWithCar() {
        Car car = carService.findOrSetup(1L);

        //Assert.assertNotNull("Car is null", car);
    }

    @Test
    public void test5AssignDriver() {

        Car car = carService.findOrSetup(1L);

        boolean success = false;
        try {
            car = carService.assignDriver(car.getCurrentOwnership().getDriver(), car);
        } catch (DriverAssignmentException e) {
            success = true;
        }

        if (!success) {
            //Assert.fail("No exception thrown when assigning the same driver");
        }

        Driver previousDriver = car.getCurrentOwnership().getDriver();

        Driver driver = new Driver();
        driver.setFirstName("DriverTwo");

        try {
            car = carService.assignDriver(driver, car);
        } catch (DriverAssignmentException e) {
            Logger.getLogger(CarServiceTest.class.getName()).log(Level.SEVERE, null, e);
        }

        //Assert.assertEquals("New driver not set", driver.getFirstName(), car.getCurrentOwnership().getDriver().getFirstName());
        //Assert.assertEquals("Wrong amount of past ownerships", 2, car.getPastOwnerships().size());
        //Assert.assertEquals("Old driver not set", previousDriver.getFirstName(), car.getPastOwnerships().get(0).getDriver().getFirstName());
    }
}
