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
import main.domain.Ownership;
import main.domain.Rate;
import main.service.CarService;
import tests.TestHelper;

import static org.junit.Assert.assertEquals;

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
    public void testGettersSetters() {
        Ownership o1 = new Ownership();
        Ownership o2 = new Ownership();
        o1.setId(1L);
        o2.setId(2L);

        Rate rate = new Rate();
        rate.setName("Rate");

        Car car = new Car();
        car.setId(1L);
        car.setLicencePlate("AA-11-AA");
        car.setCartrackerId(2L);
        car.setCurrentOwnership(o1);
        car.getPastOwnerships().add(o2);
        car.setRate(rate);

        assertEquals(new Long(1L), car.getId());
        assertEquals("AA-11-AA", car.getLicencePlate());
        assertEquals(new Long(2L), car.getCartrackerId());
        assertEquals(o2, car.getPastOwnerships().get(0));
        assertEquals("Rate", car.getRate().getName());
    }

    @Test
    public void createCarTest() {
        //Create car and check if certain values that must not be null are not null.
        Car car = new Car();

        Assert.assertNotNull("List of car ownerships is not instantiated", car.getPastOwnerships());

        //Create a car
        carService.create(car);
        Assert.assertEquals("Car is not created", 1, carService.getAll().size());
        Assert.assertNotNull("Car Id is not set by the database", car.getId());
    }
}
