/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.beans;

import java.io.Serializable;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import main.domain.Rate;
import main.domain.User;
import main.service.RateService;

/**
 *
 * @author martijn
 */
@Named
@ViewScoped
public class KilometerRateBean implements Serializable{
    
    @Inject
    private RateService rateService;
    
    public void editRate(User user, Rate rate) {
        if(user.getPermissions().isEmpty()) {
            rateService.update(rate);
        }
    }
    
    public void addRate(User user, Rate rate) {
        if(user.getPermissions().isEmpty()) {
            rateService.create(rate);
        }
    }
    
    public void removeRate(User user, Rate rate) {
        if(user.getPermissions().isEmpty()) {
            rateService.remove(rate);
        }
    }
}
