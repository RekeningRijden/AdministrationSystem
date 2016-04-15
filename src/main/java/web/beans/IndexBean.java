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
import main.service.RateService;

/**
 *
 * @author maikel
 */
@Named
@ViewScoped
public class IndexBean implements Serializable {

    @Inject
    private RateService rateService;

    public void init() {
        if (rateService.getAll().isEmpty()) {
            rateService.create(new Rate("A"));
            rateService.create(new Rate("B"));
            rateService.create(new Rate("C"));
            rateService.create(new Rate("D"));
            rateService.create(new Rate("E"));
        }
    }

}
