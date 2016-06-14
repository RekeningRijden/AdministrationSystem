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
        return super.login(username, PasswordGenerator.encryptPassword(username, password));
    }

    /**
     * Create a new User with the given username and password.
     * Encrypt the password and add the User to the database.
     *
     * @param username for the new User.
     * @param password for the new User.
     */
    public void register(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(PasswordGenerator.encryptPassword(username, password));

        create(user);
    }
}
