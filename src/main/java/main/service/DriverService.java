package main.service;

import main.core.communcation.Communicator;
import main.dao.DriverDao;
import main.domain.Address;
import main.domain.Driver;

import javax.ejb.Stateless;

import java.io.Serializable;

/**
 * @author Sam
 */
@Stateless
public class DriverService extends DriverDao implements Serializable {

    @Override
    protected Class<Driver> getEntityClass() {
        return Driver.class;
    }

    /**
     * Find a Driver by the given id in the database. When no driver is found set up a new one.
     *
     * @param id to find the Driver with.
     * @return the found or newly created driver.
     */
    public Driver findOrSetup(Long id) {
        return id == null ? new Driver() : findById(id);
    }

    /**
     * Find a Driver by it's id and when found set it's address.
     *
     * @param driverId  the id to find the Driver with.
     * @param newDriver to get the address from.
     * @return the updated Driver.
     */
    public Driver findAndSetAddress(Long driverId, Driver newDriver) {
        Driver driver = findById(driverId);
        if (driver == null) {
            return null;
        }

        driver.setAddress(newDriver.getAddress());
        return update(driver);
    }

    /**
     * Create or update a Driver to the database.
     *
     * @param driver to save to the database.
     * @return the updated Driver.
     * @throws Exception when  something went wrong with the communication with the BillDriverApplication.
     */
    public Driver createOrUpdate(Driver driver) throws Exception {
        if (hasBeenPersisted(driver)) {
            return update(driver);
        } else {
            driver = create(driver);
            flush();
            Communicator.addDriver(driver);
        }

        return driver;
    }
}
