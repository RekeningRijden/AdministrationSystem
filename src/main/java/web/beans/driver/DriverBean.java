package web.beans.driver;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import main.domain.Driver;
import main.service.DriverService;
import web.core.helpers.ContextHelper;
import web.core.helpers.FrontendHelper;
import web.core.helpers.RedirectHelper;
import web.core.helpers.Validator;

/**
 * @author maikel
 */
@Named
@ViewScoped
public class DriverBean implements Serializable {

    @Inject
    private DriverService driverService;

    private Long driverId;
    private Driver driver;

    public void init() {
        if (!ContextHelper.isAjaxRequest()) {
            driver = driverService.findOrSetup(driverId);
        }
    }

    public void save() {
        if (Validator.validDriver(driver)) {
            boolean success = true;

            try {
                driver = driverService.createOrUpdate(driver);
            } catch (Exception e) {
                Logger.getLogger(DriverBean.class.getName()).log(Level.SEVERE, null, e);
                FrontendHelper.displayErrorSmallBox("Driver could not be added");
                success = false;
            }

            if (success) {
                RedirectHelper.redirect("/pages/driver/driverOverview.xhtml");
            }
        } else {
            FrontendHelper.displayErrorSmallBox("Please fill in all the required fields");
        }
    }

    //<editor-fold defaultstate="collapsed" desc="Getters / Setters">
    public Long getDriverId() {
        return driverId;
    }

    public void setDriverId(Long driverId) {
        this.driverId = driverId;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }
    //</editor-fold>
}
