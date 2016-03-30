package web.beans;



import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Named;

/**
 *
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
