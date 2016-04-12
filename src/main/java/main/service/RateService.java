/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.service;

import java.io.Serializable;
import javax.ejb.Stateless;
import main.dao.RateDao;
import main.domain.Rate;

/**
 *
 * @author martijn
 */
@Stateless
public class RateService extends RateDao implements Serializable {

    @Override
    protected Class<Rate> getEntityClass() {
        return Rate.class;
    }
    
}
