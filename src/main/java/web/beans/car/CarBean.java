/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.beans.car;

import org.codehaus.jettison.json.JSONException;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import main.core.communcation.Communicator;
import main.domain.Car;
import main.domain.Driver;
import main.domain.Ownership;
import main.domain.Rate;
import main.service.CarService;
import main.service.DriverService;
import main.service.RateService;
import web.core.helpers.ContextHelper;
import web.core.helpers.FrontendHelper;
import javax.faces.view.ViewScoped;
import web.core.helpers.RedirectHelper;

/**
 * @author maikel
 */
@Named
@SessionScoped
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
            if (carId != null) {
                car = carService.findById(carId);
            } else {
                car = new Car();

                Ownership ownership = new Ownership();
                ownership.setDriver(new Driver());
                ownership.setCar(car);
                ownership.setStartDate(new Date());

                car.setCurrentOwnership(ownership);
            }

            drivers = driverService.getAll();
            rates = rateService.getAll();
        }
    }

    public void save() {
        if (car.getCartrackerId() != null && carService.hasBeenPersisted(car)) {
            car = carService.update(car);
            FrontendHelper.displaySuccessSmallBox("De auto is geüpdate");
            RedirectHelper.redirect("/pages/car/carOverview.xhtml");
        } else {
            if (car.getCurrentOwnership().getDriver() != null) {
                try {
                    car.setCartrackerId(Communicator.requestNewCartracker());
                    car.getPastOwnerships().add(car.getCurrentOwnership());
                    car = carService.update(car);
                    FrontendHelper.displaySuccessSmallBox("De auto is toegevoegd");
                    RedirectHelper.redirect("/pages/car/carOverview.xhtml");
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                    FrontendHelper.displayErrorSmallBox("De auto kon niet toegevoegd worden");
                }
            } else {
                FrontendHelper.displayErrorSmallBox("Selecteer een bestuurder");
            }
        }
    }

    public void updateSelectedDriver() {
        selectedDriver = car.getCurrentOwnership().getDriver();
    }

    public void saveDriver() {
        if (selectedDriver.equals(car.getCurrentOwnership().getDriver())) {
            FrontendHelper.displayErrorSmallBox("Dit is al de bestuurder");
        } else {
            if (car.getCurrentOwnership().getId() != null) {
                car.getCurrentOwnership().setEndDate(new Date());
                car = carService.update(car);

                Ownership ownership = new Ownership();
                ownership.setDriver(selectedDriver);
                ownership.setCar(car);
                ownership.setStartDate(new Date());

                car.setCurrentOwnership(ownership);
                car.getPastOwnerships().add(car.getCurrentOwnership());

                car = carService.update(car);
            }else{
                car.getCurrentOwnership().setDriver(selectedDriver);
            }
        }
        FrontendHelper.hideModal("newDriverModal");
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
