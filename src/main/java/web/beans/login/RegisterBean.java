package web.beans.login;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import main.service.UserService;

/**
 * @author Martijn
 */
@Named
@ViewScoped
public class RegisterBean implements Serializable {

    @Inject
    private UserService userService;

    private String username;
    private String password;

    public void register() {
        userService.register(username, password);
    }

    //<editor-fold defaultstate="collapsed" desc="Getters / Setters">
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
    //</editor-fold>
}
