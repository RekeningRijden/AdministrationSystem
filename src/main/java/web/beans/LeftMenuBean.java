package web.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Named;

import java.io.Serializable;

/**
 * @author administration
 */
@ManagedBean
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
