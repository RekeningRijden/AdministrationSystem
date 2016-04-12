/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.dao;

import javax.persistence.TypedQuery;
import main.domain.Driver;

/**
 * @author Sam
 */
public abstract class DriverDao extends AbstractDao<Driver> {
    
    public Driver getByName(String name) {
        TypedQuery<Driver> q = getEntityManager().createQuery("SELECT d FROM Driver d WHERE concat(d.firstName, ' ', d.lastName) = :name", Driver.class)
                .setParameter("name", name);

        return q.getResultList().isEmpty() ? null : q.getResultList().get(0);
    }
    
}
