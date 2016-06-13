/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.beans.driver;

import org.codehaus.jettison.json.JSONException;

import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import main.core.communcation.Communicator;
import main.domain.Address;
import main.domain.Driver;
import main.service.DriverService;
import web.core.helpers.ContextHelper;
import web.core.helpers.FrontendHelper;
import web.core.helpers.RedirectHelper;

/**
 * @author maikel
 */
@Named
@SessionScoped
public class DriverBean implements Serializable {

    @Inject
    private DriverService driverService;

    private Long driverId;
    private Driver driver;

    //@PostConstruct
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
        if (driver.getFirstName().equals("") && driver.getLastName().equals("") && driver.getEmail().equals("") && driver.getAddress().getStreet().equals("") && driver.getAddress().getStreetNr().equals("") && driver.getAddress().getZipCode().equals("") && driver.getAddress().getCountry().equals("") && driver.getAddress().getCity().equals("")) {
            FrontendHelper.displayErrorSmallBox("All field must be filled");
        } else {
            if (driverService.hasBeenPersisted(driver)) {
                driverService.update(driver);
            } else {
                driver = driverService.create(driver);
                //register in bill driver application

                try {
                    Communicator.addDriver(driver);
                } catch (Exception e) {
                    Logger.getLogger(DriverBean.class.getName()).log(Level.SEVERE, null, e);

                    FrontendHelper.displayErrorSmallBox("Driver could not be added");
                }
            }
            RedirectHelper.redirect("/pages/driver/driverOverview.xhtml");
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
