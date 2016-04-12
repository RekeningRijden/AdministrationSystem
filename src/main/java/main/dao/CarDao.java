/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.dao;

import main.domain.Car;
import main.domain.enums.SortOrder;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import java.util.List;

/**
 * @author Sam
 */
public abstract class CarDao extends AbstractDao<Car> {

    public List<Car> getSortedFilteredAndPaged(int first, int pageSize,
                                               String sortValue, SortOrder sortOrder, String filter) {

        String queryString = "FROM Car c LEFT JOIN c.currentOwnership.driver d";
        queryString += filter.isEmpty() ? "" : getFilteredQueryString();
        queryString += sortValue.isEmpty() ? "" : getSortedQueryString(sortValue, sortOrder);

        TypedQuery<Car> query = getEntityManager().createQuery(queryString, Car.class);
        if (!filter.isEmpty()) {
            query.setParameter("filter", "%" + filter + "%");
        }

        return query.setFirstResult(first).setMaxResults(pageSize).getResultList();
    }

    public int getFilteredRowCount(String filter) {
        String queryString = "SELECT COUNT(c.id) FROM Car c LEFT JOIN c.currentOwnership.driver d";
        queryString += filter.isEmpty() ? "" : getFilteredQueryString();

        Query query = getEntityManager().createQuery(queryString);
        if (!filter.isEmpty()) {
            query.setParameter("filter", "%" + filter + "%");
        }

        return (int) (long) query.getSingleResult();
    }

    private String getFilteredQueryString() {
        return " WHERE c.licencePlate LIKE :filter OR d.lastName LIKE :filter";
    }

    public List<Car> getCarsFromDriverWithId(Long driverId) {
        return getEntityManager().createNamedQuery("findAllCarsFromDriverWithId", Car.class)
                .setParameter("driverId", driverId)
                .getResultList();
    }
}
