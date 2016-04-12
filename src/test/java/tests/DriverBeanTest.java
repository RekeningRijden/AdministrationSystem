package tests;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import main.domain.Driver;
import main.service.DriverService;

/**
 * @author Sam
 */
@RunWith(Arquillian.class)
public class DriverBeanTest {

    @Deployment
    public static Archive<?> createDeployment() {
        return TestHelper.createDeployment();
    }

    @Inject
    private DriverService driverService;

    @After
    public void after() {
        for (Driver driver : driverService.getAll()) {
            driverService.remove(driver);
        }
    }

    @Test
    public void placeHolderTest() {

    }
}
