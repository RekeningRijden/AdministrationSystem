package service;

import java.io.Serializable;

import javax.ejb.Stateless;

import dao.DriverDao;
import domain.Driver;

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
