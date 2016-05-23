/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.beans.rate;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import main.domain.Rate;
import main.domain.User;
import main.service.RateService;
import web.core.helpers.ContextHelper;

/**
 * @author martijn
 */
@Named
@ViewScoped
public class KilometerRateBean implements Serializable {

    @Inject
    private RateService rateService;

    private List<Rate> rates;

    private User user;

    private List<String> checkPermissions;

    private Rate rate;

    @PostConstruct
    public void init() {
        if (!ContextHelper.isAjaxRequest()) {
            this.rates = rateService.getAll();
            this.user = new User();
        }
    }

    public void editRate() {
        rateService.update(rate);
    }

    public void addEnergyLabel() {
        rateService.create(rate);
        this.rates = rateService.getAll();
    }

    public void removeRate() {
        rateService.remove(rate);
    }

    public void prepareNewRateCreation() {
        rate = new Rate();
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
    //</editor-fold>
}
