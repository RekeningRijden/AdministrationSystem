package main.service;

import java.io.Serializable;

import javax.ejb.Stateless;

import domain.User;
import main.dao.UserDao;

/**
 * @author Sam
 */
@Stateless
public class UserService extends UserDao implements Serializable {

    @Override
    protected Class<User> getEntityClass() {
        return User.class;
    }
}
