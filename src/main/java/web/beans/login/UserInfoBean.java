package web.beans.login;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import main.domain.User;
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
}
