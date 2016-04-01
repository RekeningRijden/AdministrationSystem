/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.beans.car;

import com.ceron.gatevvem.web.core.helper.ContextHelper;
import domain.Car;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import main.service.CarService;

/**
 *
 * @author maikel
 */
@ManagedBean
@Named
@ViewScoped
public class CarBean implements Serializable {

    @Inject
    private CarService carService;

    private Long carId;
    private Car car;

    public void init() {
        if (!ContextHelper.isAjaxRequest()) {
            if (carId != null) {
                car = carService.findById(carId);
            }else{
                car = new Car();
            }
        }
    }
    
    public void save(){
        if(carService.hasBeenPersisted(car)){
            carService.update(car);
        }else{
            carService.create(car);
        }
    }

    //<editor-fold defaultstate="collapsed" desc="getters and setters">
    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }
    //</editor-fold>    

}
