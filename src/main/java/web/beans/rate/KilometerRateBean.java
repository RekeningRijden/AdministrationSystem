/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.beans.rate;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import main.domain.Rate;
import main.domain.User;
import main.domain.enums.Permission;
import main.service.RateService;
import web.core.helpers.ContextHelper;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author martijn
 */
@ManagedBean
@Named
@ViewScoped
public class KilometerRateBean implements Serializable {
    
    @Inject
    private RateService rateService;

    private List<Rate> rates;

    private User user;

    private List<String> checkPermissions;

    private Rate rate;

    private String energyLabel;

    private String energyLabelRateValue;

    @PostConstruct
    public void init() {
        if(!ContextHelper.isAjaxRequest()) {
            this.rates = rateService.getAll();
            this.user = new User();
        }
    }
    
    public void editRate() {
        rateService.update(rate);
    }
    
    public void addEnergyLabel() {
        Rate rate = new Rate(energyLabel);
        rate.setValue(energyLabelRateValue);
        rateService.create(rate);
    }
    
    public void removeRate() {
        rateService.remove(rate);
    }

    public String getPermissions() {
        return Permission.values()[0].toString();
    }

    public void editPermissions(User user) {
        List<Permission> permissions = user.getPermissions();
        permissions.clear();

        for(String permission : checkPermissions)
        {
            if(Objects.equals(permission, "KM_PRICE")) {
                permissions.add(Permission.KM_PRICE);
            }
        }
        user.setPermissions(permissions);
    }

    //<editor-fold defaultstate="collapsed" desc="Getters / Setters">
    public List<Rate> getRates() {
        return rates;
    }

    public void setRates(List<Rate> rates) {
        this.rates = rates;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<String> getCheckPermissions() {
        return checkPermissions;
    }

    public void setCheckPermissions(List<String> checkPermissions) {
        this.checkPermissions = checkPermissions;
    }

    public Rate getRate() {
        return rate;
    }

    public void setRate(Rate rate) {
        this.rate = rate;
    }

    public String getEnergyLabel() {
        return energyLabel;
    }

    public void setEnergyLabel(String energyLabel) {
        this.energyLabel = energyLabel;
    }

    public String getEnergyLabelRateValue() {
        return energyLabelRateValue;
    }

    public void setEnergyLabelRateValue(String energyLabelRateValue) {
        this.energyLabelRateValue = energyLabelRateValue;
    }
    //</editor-fold>
}
