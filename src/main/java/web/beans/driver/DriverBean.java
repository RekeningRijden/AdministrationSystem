/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.beans.driver;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import main.domain.Address;
import main.domain.Driver;
import main.service.DriverService;
import web.core.helpers.ContextHelper;
import web.core.helpers.FrontendHelper;

/**
 *
 * @author maikel
 */
@ManagedBean
@Named
@SessionScoped
public class DriverBean implements Serializable {

    @Inject
    private DriverService driverService;

    private Long driverId;
    private Driver driver;

    public void init() {
        if (!ContextHelper.isAjaxRequest()) {
            if (driverId != null) {
                driver = driverService.findById(driverId);
                if (driver.getAddress() == null) {
                    Address address = new Address();
                    driver.setAddress(address);
                }
            } else {
                driver = new Driver();

                Address address = new Address();
                driver.setAddress(address);
            }
        }
    }

    public void save() {
        if (driverService.hasBeenPersisted(driver)) {
            driverService.update(driver);
            FrontendHelper.displaySuccessSmallBox("De bestuurder is geupdate");
        } else {
            driverService.create(driver);
            FrontendHelper.displaySuccessSmallBox("De bestuurder is aangemaakt");
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
