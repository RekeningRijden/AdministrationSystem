package web.beans;

import javax.inject.Named;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;

/**
 * @author administration
 */
@Named
@SessionScoped
public class LeftMenuBean implements Serializable {

    private String currentMenuItem = "index";

    public String getCurrentMenuItem() {
        return currentMenuItem;
    }

    public void setCurrentMenuItem(String currentMenuItem) {
        this.currentMenuItem = currentMenuItem;
    }
}
