package web.beans.login;

import main.core.helper.PasswordGenerator;
import main.domain.User;
import main.service.UserService;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by martijn on 10-5-2016.
 */
@Named
@ViewScoped
public class RegisterBean {

    private String username;
    private String password;

    @Inject
    private UserService userService;

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void register() {
        User u = new User();
        u.setUsername(username);
        u.setPassword(PasswordGenerator.encryptPassword(username,password));
        userService.create(u);
    }
}
