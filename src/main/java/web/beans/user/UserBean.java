package web.beans.user;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import main.domain.User;
import main.domain.enums.Permission;
import main.service.UserService;
import web.beans.login.UserInfoBean;
import web.core.helpers.ContextHelper;

/**
 * Created by martijn on 10-5-2016.
 */
@Named
@SessionScoped
public class UserBean implements Serializable {

    @Inject
    private UserService userService;

    private List<Permission> selectedPermissions;

    private Permission[] allPermissions;

    private User currentUser;

    @Inject
    private UserInfoBean userInfoBean;

    public void init() {
        if (!ContextHelper.isAjaxRequest()) {
            currentUser = userService.findById(userInfoBean.getLoggedInUser().getId());

            allPermissions = Permission.values();
            selectedPermissions = currentUser.getPermissions();
        }
    }

    public void setNewPermissions() {
        currentUser.setPermissions(selectedPermissions);
        userService.update(currentUser);
    }

    public List<User> getAllUsers() {
        return userService.getAll();
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public List<Permission> getSelectedPermissions() {
        return selectedPermissions;
    }

    public void setSelectedPermissions(List<Permission> selectedPermissions) {
        this.selectedPermissions = selectedPermissions;
    }

    public Permission[] getAllPermissions() {
        return allPermissions;
    }

    public void setAllPermissions(Permission[] allPermissions) {
        this.allPermissions = allPermissions;
    }
}
