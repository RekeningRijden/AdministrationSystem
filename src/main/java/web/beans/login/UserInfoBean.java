package web.beans.login;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import main.domain.User;
import main.domain.UserGroup;
import main.domain.enums.Permission;

/**
 * Created by martijn on 10-5-2016.
 */
@Named
@SessionScoped
public class UserInfoBean implements Serializable {

    private User loggedInUser;

    public String logout() {
        loggedInUser = null;
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();

        return "/login.xhtml?faces-redirect=true";
    }

    public synchronized User getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public String getLoggedInUsername() {
        return getLoggedInUser().getUsername();
    }

    public UserGroup getLoggedInGroup() {
        return getLoggedInUser().getUserGroup();
    }

    public List<Permission> getLoggedInPermissions() {
        return getLoggedInUser().getPermissions();
    }
}
