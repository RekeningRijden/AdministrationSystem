package service;

import java.io.Serializable;

import javax.ejb.Stateless;

import dao.UserDao;
import domain.User;

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
