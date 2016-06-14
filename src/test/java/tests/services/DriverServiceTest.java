package tests.services;

import junit.framework.Assert;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import javax.inject.Inject;

import main.core.helper.PasswordGenerator;
import main.domain.Address;
import main.domain.Car;
import main.domain.Driver;
import main.service.DriverService;
import tests.TestHelper;

/**
 * @author Sam
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(Arquillian.class)
public class DriverServiceTest {

    @Deployment
    public static Archive<?> createDeployment() {
        return TestHelper.createDeployment();
    }

    @Inject
    private DriverService driverService;

    @Test
    public void test1SetupWithoutDriver() {
        Driver driver = driverService.findOrSetup(null);

        Assert.assertNull("A driver has been found but there should be none in the database", driver.getId());
    }

    @Test
    public void test2CreateOrUpdateWithoutDriver() throws Exception {
        String email = PasswordGenerator.generateRandomPassword(5)
                + "@"
                + PasswordGenerator.generateRandomPassword(5);

        Driver driver = driverService.findOrSetup(null);
        driver.setFirstName("Henk");
        driver.setEmail(email);

        //driver = driverService.createOrUpdate(driver);

        //Assert.assertTrue("Driver has not been created in the database", driverService.hasBeenPersisted(driver));
        //Assert.assertEquals("Wrong amount of drivers found in the database", 1, driverService.getAll().size());
    }

    @Test
    public void test3SetupWithDriver() {
        Driver driver = driverService.findOrSetup(1L);

        Assert.assertEquals("Wrong driver found", "Henk", driver.getFirstName());
    }

    @Test
    public void test4CreateOrUpdateWithDriver() throws Exception {
        Driver driver = driverService.findOrSetup(1L);
        driver.setFirstName("Piet");

        driverService.createOrUpdate(driver);

        Assert.assertEquals("Wrong amount of drivers found in the database", 1, driverService.getAll().size());
    }

    @Test
    public void test5FindAndSetAddress() {
        Address address = new Address();
        address.setStreet("Henkstraat");

        Driver driver = new Driver();
        driver.setAddress(address);

        Driver result = driverService.findAndSetAddress(1L, driver);

        Assert.assertEquals("Wrong address set", "Henkstraat", result.getAddress().getStreet());
    }
}
