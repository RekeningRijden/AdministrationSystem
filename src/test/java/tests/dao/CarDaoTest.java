package tests.dao;

import junit.framework.Assert;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.util.List;

import javax.inject.Inject;

import main.domain.Car;
import main.domain.Driver;
import main.service.CarService;
import main.service.DriverService;
import tests.TestHelper;

/**
 * @author Sam
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(Arquillian.class)
public class CarDaoTest {

    @Deployment
    public static Archive<?> createDeployment() {
        return TestHelper.createDeployment();
    }

    @Inject
    private CarService carService;
    @Inject
    private DriverService driverService;

    @Test
    public void test1GetByDriverId() throws Exception {
        Driver driver = new Driver();
        driver = driverService.create(driver);

        Car car1 = carService.findOrSetup(null);
        car1.setLicencePlate("11-AA-11");
        car1.getCurrentOwnership().setDriver(driver);
        carService.createOrUpdate(car1);

        Car car2 = carService.findOrSetup(null);
        car2.setLicencePlate("11-BB-11");
        car2.getCurrentOwnership().setDriver(driver);
        carService.createOrUpdate(car2);

        Car car3 = carService.findOrSetup(null);
        car3.getCurrentOwnership().setDriver(new Driver());
        carService.createOrUpdate(car3);

        List<Car> cars = carService.getCarsFromDriverWithId(driver.getId());

        //Assert.assertEquals("Wrong amount of cars found", 2, cars.size());
        //Assert.assertEquals("Wrong cars found", "11-AA-11", cars.get(0).getLicencePlate());
        //Assert.assertEquals("Wrong cars found", "11-BB-11", cars.get(1).getLicencePlate());
    }

    @Test
    public void test2GetByCartrackerId(){
        Car car1 = carService.findOrSetup(null);
        car1.setLicencePlate("22-AA-22");
        car1.setCartrackerId(1L);
        carService.create(car1);

        Car result = carService.getCarByCartrackerId(1L);

        Assert.assertEquals("Wrong car found", car1.getLicencePlate(), result.getLicencePlate());
    }

    @Test
    public void test3GetByLicencePlate(){
        Car car1 = carService.findOrSetup(null);
        car1.setLicencePlate("33-AA-33");
        carService.create(car1);

        Car result = carService.getCarByLicencePlate("33-AA-33");

        Assert.assertEquals("Wrong car found", car1.getLicencePlate(), result.getLicencePlate());
    }
}
