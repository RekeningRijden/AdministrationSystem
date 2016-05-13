package web.beans.user;

import main.domain.User;
import main.domain.enums.Permission;
import main.service.UserService;
import web.core.helpers.ContextHelper;

import javax.annotation.PostConstruct;
import javax.faces.component.UIComponent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by martijn on 10-5-2016.
 */
@Named
@ViewScoped
public class UserBean implements Serializable {

    @Inject
    private UserService userService;

    private List<Permission> selectedPermissions;

    private Permission[] allPermissions;

    private User currentUser;

    @PostConstruct
    private void init() {
        allPermissions = Permission.values();
    }

    public void setNewPermissions() {
        List<Permission> permissions = selectedPermissions;

        currentUser.setPermissions(permissions);
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
