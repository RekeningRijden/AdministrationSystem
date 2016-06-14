package tests.dao;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import javax.inject.Inject;

import main.domain.Driver;
import main.service.DriverService;
import tests.TestHelper;

/**
 * @author Sam
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(Arquillian.class)
public class DriverDaoTest {

    @Deployment
    public static Archive<?> createDeployment() {
        return TestHelper.createDeployment();
    }

    @Inject
    private DriverService driverService;

    @Test
    public void test1GetByName() {
        String firstName = "Henk";
        String lastName = "Pietersen";

        Driver driver = new Driver();
        driver.setFirstName(firstName);
        driver.setLastName(lastName);
        driver.setEmail("henk@gmail.com");
        driverService.create(driver);

        Driver result = driverService.getByName(firstName + " " + lastName);

        Assert.assertEquals("Wrong driver found", driver.getEmail(), result.getEmail());
    }
}
