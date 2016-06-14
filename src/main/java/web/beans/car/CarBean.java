package web.beans.car;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import main.core.exception.DriverAssignmentException;
import main.domain.Car;
import main.domain.Driver;
import main.domain.Rate;
import main.service.CarService;
import main.service.DriverService;
import main.service.RateService;
import web.core.helpers.ContextHelper;
import web.core.helpers.FrontendHelper;
import web.core.helpers.RedirectHelper;

/**
 * @author maikel
 */
@Named
@ViewScoped
public class CarBean implements Serializable {

    @Inject
    private CarService carService;
    @Inject
    private DriverService driverService;
    @Inject
    private RateService rateService;

    private Long carId;
    private Car car;

    private List<Driver> drivers;
    private Driver selectedDriver;

    private List<Rate> rates;

    public void init() {
        if (!ContextHelper.isAjaxRequest()) {
            car = carService.findOrSetup(carId);
            drivers = driverService.getAll();
            rates = rateService.getAll();
        }
    }

    public void save() {
        boolean success = true;

        try {
            car = carService.createOrUpdate(car);
        } catch (DriverAssignmentException e) {
            FrontendHelper.displayErrorSmallBox("Please select a owner");
            success = false;
        } catch (Exception ex) {
            Logger.getLogger(CarBean.class.getName()).log(Level.SEVERE, null, ex);
            FrontendHelper.displayErrorSmallBox("The car could not be added");
            success = false;
        }

        carId = null;
        if (success) {
            RedirectHelper.redirect("/pages/car/carOverview.xhtml");
        }
    }

    public void assignDriver() {
        try {
            car = carService.assignDriver(selectedDriver, car);
        } catch (DriverAssignmentException e) {
            FrontendHelper.displayErrorSmallBox("Dit is al de bestuurder");
        }

        FrontendHelper.hideModal("newDriverModal");
    }

    public void updateSelectedDriver() {
        selectedDriver = car.getCurrentOwnership().getDriver();
    }

    //<editor-fold defaultstate="collapsed" desc="getters and setters">
    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public List<Rate> getRates() {
        return rates;
    }

    public void setRates(List<Rate> rates) {
        this.rates = rates;
    }

    public List<Driver> getDrivers() {
        return drivers;
    }

    public void setDrivers(List<Driver> drivers) {
        this.drivers = drivers;
    }

    public Driver getSelectedDriver() {
        return selectedDriver;
    }

    public void setSelectedDriver(Driver selectedDriver) {
        this.selectedDriver = selectedDriver;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }
    //</editor-fold>    
}
