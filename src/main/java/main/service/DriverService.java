package main.service;

import java.io.Serializable;

import javax.ejb.Stateless;

import domain.Driver;
import main.dao.DriverDao;

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
