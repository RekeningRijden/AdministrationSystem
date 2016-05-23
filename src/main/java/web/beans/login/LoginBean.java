package web.beans.login;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import main.core.helper.PasswordGenerator;
import main.domain.User;
import main.service.UserService;
import web.core.helpers.FrontendHelper;
import web.core.helpers.RedirectHelper;

/**
 * Created by martijn on 7-5-2016.
 */
@Named
@ViewScoped
public class LoginBean implements Serializable {

    @Inject
    private UserService userService;
    @Inject
    private UserInfoBean userInfoBean;

    private String username;
    private String password;

    private String to = "/pages/profile/profile.xhtml";

    public void login() {
        String typedUsername = username.toLowerCase();

        if (userInfoBean.getLoggedInUser() == null) {
            User user = userService.login(typedUsername, password);
            if (user != null) {
                userInfoBean.setLoggedInUser(user);
                RedirectHelper.redirect(to);
            } else {
                FrontendHelper.displayErrorSmallBox("Kan niet inloggen");
            }
        } else {
            FrontendHelper.displayErrorSmallBox("Kan niet inloggen");
        }
    }

    //<editor-fold defaultstate="collapsed" desc="Getters/Setters">
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String _to) {
        this.to = _to;
    }
    //</editor-fold>
}
