package service;

import java.io.Serializable;

import javax.ejb.Stateless;

import dao.CarDao;
import dao.UserDao;
import domain.Car;
import domain.User;

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
