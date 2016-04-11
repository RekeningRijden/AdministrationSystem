package main.service;

import main.dao.CarDao;
import main.domain.Car;

import javax.ejb.Stateless;

import java.io.Serializable;

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
