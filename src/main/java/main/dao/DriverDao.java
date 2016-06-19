/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.dao;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import main.domain.Driver;
import main.domain.enums.SortOrder;

/**
 * @author Sam
 */
public abstract class DriverDao extends AbstractDao<Driver> {

    public Driver getByName(String name) {
        TypedQuery<Driver> q = getEntityManager().createQuery("SELECT d FROM Driver d WHERE concat(d.firstName, ' ', d.lastName) = :name", Driver.class)
                .setParameter("name", name);

        return q.getResultList().isEmpty() ? null : q.getResultList().get(0);
    }

    public List<Driver> getSortedFilteredAndPaged(int first, int pageSize,
                                                  String sortValue, SortOrder sortOrder, String filter) {

        String queryString = "FROM Driver d";
        queryString += filter.isEmpty() ? "" : getFilteredQueryString();
        queryString += sortValue.isEmpty() ? "" : getSortedQueryString(sortValue, sortOrder);

        TypedQuery<Driver> query = getEntityManager().createQuery(queryString, Driver.class);
        if (!filter.isEmpty()) {
            query.setParameter("filter", "%" + filter + "%");
        }

        return query.setFirstResult(first).setMaxResults(pageSize).getResultList();
    }

    public int getFilteredRowCount(String filter) {
        String queryString = "SELECT COUNT(d.id) FROM Driver d";
        queryString += filter.isEmpty() ? "" : getFilteredQueryString();

        Query query = getEntityManager().createQuery(queryString);
        if (!filter.isEmpty()) {
            query.setParameter("filter", "%" + filter + "%");
        }

        return (int) (long) query.getSingleResult();
    }

    private static String getFilteredQueryString() {
        return " WHERE d.lastName LIKE :filter OR d.firstName LIKE :filter";
    }
}
