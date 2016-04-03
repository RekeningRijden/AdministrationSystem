package main.service;

import java.io.Serializable;

import javax.ejb.Stateless;

import main.dao.CarDao;
import main.domain.Car;

/**
 * @author Sam
 */
@Stateless
public class CarService extends CarDao implements Serializable {

    @Override
    protected Class<Car> getEntityClass() {
        return Car.class;
    }
}
