package main.service;

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

    public Driver findOrSetup(Long id) {
        return id == null ? new Driver() : findById(id);
    }

    public Driver updateDriver(Long driverId, Driver newDriver) {
        Driver driver = findById(driverId);
        if (driver == null) {
            return null;
        }
        driver.setAddress(newDriver.getAddress());
        return update(driver);
    }
}
