package main.core.scheduling;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import main.core.helper.PasswordGenerator;
import main.domain.User;
import main.domain.UserGroup;
import main.service.UserGroupService;
import main.service.UserService;

/**
 * @author Sam
 */
@Startup
@Singleton
public class Initializer {

    @Inject
    private UserService userService;
    @Inject
    private UserGroupService userGroupService;

    @PostConstruct
    public void createUser() {
        if (userService.getAll().isEmpty()) {
            UserGroup userGroup = new UserGroup();
            userGroup.setName("administrators");

            userGroupService.create(userGroup);

            User user = new User();
            user.setUsername("admin");
            user.setPassword(PasswordGenerator.encryptPassword(user.getUsername(), "admin"));
            user.setUserGroup(userGroup);
            userService.create(user);
        }
    }
}
