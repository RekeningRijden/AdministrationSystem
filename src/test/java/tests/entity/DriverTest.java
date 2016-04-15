package tests.entity;

import junit.framework.Assert;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import main.domain.Car;
import main.domain.Driver;
import main.service.CarService;
import main.service.DriverService;
import tests.TestHelper;

/**
 * @author Sam
 */
@RunWith(Arquillian.class)
public class DriverTest {

    @Deployment
    public static Archive<?> createDeployment() {
        return TestHelper.createDeployment();
    }

    @Inject
    private DriverService driverService;
    @Inject
    private CarService carService;

    @After
    public void after() {
        for (Driver driver : driverService.getAll()) {
            driverService.remove(driver);
        }
    }

    @Test
    public void createDriverTest() {
        //Create driver and check if certain values that must not be null are not null.
        Driver driver = new Driver();

        Assert.assertNotNull("List of driver ownerships is not instantiated", driver.getOwnerships());

        //Create a driver
        driverService.create(driver);
        Assert.assertEquals("Driver is not created", 1, driverService.getAll().size());
        Assert.assertNotNull("Driver Id is not set by the database", driver.getId());
    }
}
