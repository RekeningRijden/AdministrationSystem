/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.beans.car;

import main.domain.Car;
import main.domain.Driver;
import main.domain.Ownership;
import main.service.CarService;
import web.core.helpers.ContextHelper;
import web.core.helpers.FrontendHelper;

import javax.faces.bean.ManagedBean;
import javax.inject.Inject;
import javax.inject.Named;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.faces.bean.SessionScoped;
import main.service.DriverService;

/**
 * @author maikel
 */
@ManagedBean
@Named
@SessionScoped
public class CarBean implements Serializable {

    @Inject
    private CarService carService;
    @Inject
    private DriverService driverService;

    private Long carId;
    private Car car;

    private List<Driver> drivers;
    private Driver selectedDriver;

    public void init() {
        if (!ContextHelper.isAjaxRequest()) {
            drivers = driverService.getAll();
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
        }
    }

    public void save() {
        if (carService.hasBeenPersisted(car)) {
            carService.update(car);
            FrontendHelper.displaySuccessSmallBox("De auto is geupdate");
        } else {
            car.getPastOwnerships().add(car.getCurrentOwnership());
            carService.create(car);
            FrontendHelper.displaySuccessSmallBox("De auto is aangemaakt");
        }
    }
    
    public void updateDriver(){
        selectedDriver = car.getCurrentOwnership().getDriver();
    }

    public void saveDriver() {
        if (selectedDriver.equals(car.getCurrentOwnership().getDriver())) {
            FrontendHelper.displayErrorSmallBox("Dit is al de bestuurder");
        } else {
            car.getCurrentOwnership().setEndDate(new Date());
            car = carService.update(car);

            Ownership ownership = new Ownership();
            ownership.setDriver(selectedDriver);
            ownership.setCar(car);
            ownership.setStartDate(new Date());

            car.setCurrentOwnership(ownership);
            car.getPastOwnerships().add(car.getCurrentOwnership());

            car = carService.update(car);
        }
    }

    //<editor-fold defaultstate="collapsed" desc="getters and setters">
    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
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
