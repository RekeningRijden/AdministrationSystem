package main.service;

import main.dao.DriverDao;
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
}
