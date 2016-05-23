package main.core.scheduling;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import main.core.helper.PasswordGenerator;
import main.domain.User;
import main.service.UserService;

/**
 * @author Sam
 */
@Startup
@Singleton
public class Initializer {

    @Inject
    private UserService userService;

    @PostConstruct
    public void createUser() {
        if (userService.getAll().isEmpty()) {
            User user = new User();
            user.setUsername("admin");
            user.setPassword(PasswordGenerator.encryptPassword("admin", "admin"));
            userService.create(user);
        }
    }
}
