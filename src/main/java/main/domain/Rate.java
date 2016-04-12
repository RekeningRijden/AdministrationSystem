/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.domain;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author martijn
 */
@Entity
@Table(name = "Rates")
public class Rate implements Serializable, IEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
   
    private String rateValue;
    
    public Rate() {
        
    }
    
    public Rate(String rateValue) {
        this.rateValue = rateValue;
    }
    
    //<editor-fold defaultstate="collapsed" desc="Getters/Setters">
    @Override
    public Long getId() {
        return id;
    }
    
    public String getRateValue() {
        return rateValue;
    }

    public void setRateValue(String rateValue) {
        this.rateValue = rateValue;
    }
    //</editor-fold>
}
