package main.service;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.Query;

import main.core.communcation.Communicator;
import main.core.exception.DriverAssignmentException;
import main.dao.CarDao;
import main.domain.Car;
import main.domain.Driver;
import main.domain.Ownership;

/**
 * @author Sam
 */
@Stateless
public class CarService extends CarDao implements Serializable {

    @Override
    protected Class<Car> getEntityClass() {
        return Car.class;
    }

    /**
     * Find a Car in the database with the given id. Setup a new Car if no Car can be found.
     *
     * @param id to search te database with.
     * @return the found or created Car.
     */
    public Car findOrSetup(Long id) {
        if (id == null) {
            Car car = new Car();

            Ownership ownership = new Ownership();
            ownership.setCar(car);
            ownership.setStartDate(new Date());

            car.setCurrentOwnership(ownership);
            return car;
        } else {
            return findById(id);
        }
    }

    /**
     * Add a Driver as an Ownership to a Car.
     *
     * @param driver to add.
     * @param car    to add to.
     * @return the Car with the Driver added to it.
     * @throws DriverAssignmentException when the Driver is already the owner of the Car.
     */
    public Car assignDriver(Driver driver, Car car) throws DriverAssignmentException {
        if (driver.equals(car.getCurrentOwnership().getDriver())) {
            throw new DriverAssignmentException("This driver is already assigned to the car");
        } else {
            if (car.getCurrentOwnership().getId() != null) {
                car.getCurrentOwnership().setEndDate(new Date());

                Ownership ownership = new Ownership();
                ownership.setDriver(driver);
                ownership.setCar(car);
                ownership.setStartDate(new Date());

                car.getPastOwnerships().add(car.getCurrentOwnership());
                car.setCurrentOwnership(ownership);

                return update(car);
            } else {
                car.getCurrentOwnership().setDriver(driver);
            }
        }

        return car;
    }

    /**
     * Create or update a Car to the database.
     *
     * @param car to save to the database.
     * @return the updated Car.
     * @throws Exception when the car does not have a Driver attached to it or something went
     *                   wrong with the communication with the MovementSystem.
     */
    public Car createOrUpdate(Car car) throws Exception {
        if (car.getCartrackerId() != null && hasBeenPersisted(car)) {
            return update(car);
        } else {
            if (car.getCurrentOwnership().getDriver() == null) {
                throw new DriverAssignmentException("Car does not have an ownership yet");
            } else {
                car.setCartrackerId(Communicator.requestNewCartracker());
                car.getPastOwnerships().add(car.getCurrentOwnership());
                return create(car);
            }
        }
    }


}
