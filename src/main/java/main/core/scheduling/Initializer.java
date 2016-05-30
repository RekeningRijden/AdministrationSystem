package main.core.scheduling;

import java.math.BigDecimal;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import main.core.helper.PasswordGenerator;
import main.domain.Rate;
import main.domain.Region;
import main.domain.User;
import main.domain.UserGroup;
import main.service.RateService;
import main.service.RegionService;
import main.service.UserGroupService;
import main.service.UserService;

/**
 * @author Sam
 */
@Startup
@Singleton
public class Initializer {

    @Inject
    private RateService rateService;
    @Inject
    private RegionService regionService;
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

        if (rateService.getAll().isEmpty()) {
            rateService.create(new Rate("A"));
            rateService.create(new Rate("B"));
            rateService.create(new Rate("C"));
            rateService.create(new Rate("D"));
            rateService.create(new Rate("E"));
        }

        if (regionService.getAll().isEmpty()) {
            regionService.create(new Region("NORTE", 40.5, new BigDecimal(0.2)));
            regionService.create(new Region("CENTRO", 39.5, new BigDecimal(0.25)));
            regionService.create(new Region("ALENTEJO", 38.0, new BigDecimal(0.3)));
            regionService.create(new Region("ALGARVE", 37.2, new BigDecimal(0.35)));
        }
    }
}