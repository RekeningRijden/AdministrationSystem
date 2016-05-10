package main.service;

import main.core.helper.PasswordGenerator;
import main.dao.UserDao;
import main.domain.User;

import javax.ejb.Stateless;

import java.io.Serializable;

/**
 * @author Sam
 */
@Stateless
public class UserService extends UserDao implements Serializable {

    @Override
    protected Class<User> getEntityClass() {
        return User.class;
    }

    @Override
    public User login(String username, String password) {
        return super.login(username, PasswordGenerator.encryptPassword(username,password));
    }
}
