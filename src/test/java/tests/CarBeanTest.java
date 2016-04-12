package tests;

import junit.framework.Assert;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import main.domain.Car;
import main.service.CarService;
import web.beans.car.CarBean;

/**
 * @author Sam
 */
@RunWith(Arquillian.class)
public class CarBeanTest {

    @Deployment
    public static Archive<?> createDeployment() {
        return TestHelper.createDeployment();
    }

    @Before
    public void before() {

    }

    @After
    public void after() {
        for (Car car : carService.getAll()) {
            carService.remove(car);
        }
    }

    @Inject
    private CarService carService;
    @Inject
    private CarBean carBean;

    @Test
    public void testInitialisationWithoutCar() {
        carBean.init();

        //Test if a new Car object has been created.
        Assert.assertNotNull("A new Car has not been created", carBean.getCar());
//        Assert.assertNotNull("A new ownership has not beed added to a car", carBean.getCar().getCurrentOwnerShip());
//        Assert.assertNotNull("A new Driver has not been added to an Ownership", carBean.getCar().getCurrentOwnership().getDriver());
    }

    @Test
    public void testInitialisationWithCar() {
        //Create a new Car and persist is
        Car car = new Car();
        car.setLicencePlate("AB-123-C");

        car = carService.create(car);

        //Test if the car is retrieved when the CarBean is initialised
        carBean.setCarId(car.getId());
        carBean.init();

        Assert.assertNotNull("A new Car has not been retrieved", carBean.getCar());
    }

    @Test
    public void testOwnershipCreation() {

    }
}
