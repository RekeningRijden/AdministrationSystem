package tests.entity;

import junit.framework.Assert;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import main.domain.Address;
import main.domain.Car;
import main.domain.Driver;
import main.domain.Ownership;
import main.service.CarService;
import main.service.DriverService;
import tests.TestHelper;

import static org.junit.Assert.assertEquals;

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
    public void testGettersSetters() {
        Address address = new Address();
        address.setId(3L);

        Ownership ownership = new Ownership();
        ownership.setId(4L);

        Driver driver = new Driver();
        driver.setId(1L);
        driver.setEmail("henk@gmail.com");
        driver.setFirstName("Henk");
        driver.setLastName("Pietersen");
        driver.setAddress(address);
        driver.getOwnerships().add(ownership);

        assertEquals(new Long(1L), driver.getId());
        assertEquals("henk@gmail.com", driver.getEmail());
        assertEquals("Henk", driver.getFirstName());
        assertEquals("Pietersen", driver.getLastName());
        assertEquals(address.getId(), driver.getAddress().getId());
        assertEquals(ownership.getId(), driver.getOwnerships().get(0).getId());
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
