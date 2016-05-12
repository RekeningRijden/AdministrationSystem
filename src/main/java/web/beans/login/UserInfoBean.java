package web.beans.login;

import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import main.domain.User;
import main.domain.UserGroup;
import main.domain.enums.Permission;
import web.core.helpers.RedirectHelper;

/**
 * Created by martijn on 10-5-2016.
 */
@Named
@SessionScoped
public class UserInfoBean implements Serializable {

    private User loggedInUser;

    public void logout() {
        loggedInUser = null;
        RedirectHelper.redirect("/index.xhtml?faces-redirect=true");
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
