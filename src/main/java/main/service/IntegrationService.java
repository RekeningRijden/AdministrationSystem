package main.service;

import java.io.Serializable;
import main.domain.Car;
import main.domain.Driver;
import main.domain.Ownership;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Eric on 24-06-16.
 */
@Named
public class IntegrationService implements Serializable{

    @Inject
    private CarService carService;

    @Inject
    private DriverService driverService;

    @Inject
    private OwnershipService ownershipService;

    @Inject
    private RateService rateService;

    /**
     * Returns true if the car and driver are created (or updated) and has an
     * ownership
     *
     * @param car The car to be registered.
     * @param driver The driver of the car.
     * @return True if the car, driver and ownership are succesfully created or
     * updated
     */
    public boolean AddNewForeignCarWithDriverAndOwnership(Car car, Driver driver) {
        // Set the rate of the car, Foreign cars always have Rate E.
        car.setRate(rateService.getByName("E"));

        //Check if the driver exists and update it, otherwise create a new driver
        Driver d = driverService.getByName(driver.getFullName());
        if (d == null) {
            driver = driverService.create(driver);
        } else {
            driver = driverService.update(d);
        }

        //Check if the car exists and update it, otherwise create a new car
        Car c = checkIfCarExists(car);
        if (c == null) {
            car = carService.create(car);
        } else {
            car = carService.update(c);
        }

        // Check if the car is currently owned by the driver, if not then
        if (car.getCurrentOwnership() == null || !car.getCurrentOwnership().getDriver().equals(driver)) {
            if (car.getPastOwnerships().size() > 0 && car.getCurrentOwnership() != null) {
                car.getPastOwnerships().remove(car.getCurrentOwnership());
                car.getCurrentOwnership().setEndDate(new Date());
                car.getPastOwnerships().add(car.getCurrentOwnership());
            }

            Ownership ownership = new Ownership();
            ownership.setCar(car);
            ownership.setDriver(driver);
            ownership.setStartDate(new Date());

            //Use update (merge) not create (persist) otherwise driver and address are added multiple times in the database
            car.setCurrentOwnership(ownership);
            carService.update(car);
        }

        return true;

    }

    public Car checkIfCarExists(Car car) {
        Car existingCar = carService.getCarByCartrackerId(car.getCartrackerId());
        if (existingCar != null) {
            return existingCar;
        }

        existingCar = carService.getCarByLicencePlate(car.getLicencePlate());
        if (existingCar != null) {
            return existingCar;
        }

        return null;
    }

}
