package tests;

import junit.framework.Assert;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import main.domain.Car;
import main.service.CarService;

/**
 * @author Sam
 */
@RunWith(Arquillian.class)
public class CarTest {

    @Deployment
    public static Archive<?> createDeployment() {
        return TestHelper.createDeployment();
    }

    @After
    public void after() {
        for (Car car : carService.getAll()) {
            carService.remove(car);
        }
    }

    @Inject
    private CarService carService;

    @Test
    public void createCarTest() {
        //Create car and check if certain values that must not be null are not null.
        Car car = new Car();

        Assert.assertNotNull("List of car ownerships is not instantiated", car.getOwnerships());

        //Create a car
        carService.create(car);
        Assert.assertEquals("Car is not created", 1, carService.getAll().size());
        Assert.assertNotNull("Car Id is not set by the database", car.getId());
    }
}
