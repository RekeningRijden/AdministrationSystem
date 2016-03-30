/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.dao;

import main.dao.AbstractDao;
import domain.Car;
import domain.enums.SortOrder;
import java.util.List;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 * @author Sam
 */
public abstract class CarDao extends AbstractDao<Car> {

    public List<Car> getSortedFilteredAndPaged(int first, int pageSize,
            String sortValue, SortOrder sortOrder, String filter) {

        String queryString = "FROM Car c";
        queryString += filter.isEmpty() ? "" : getFilteredQueryString();
        queryString += sortValue.isEmpty() ? "" : getSortedQueryString(sortValue, sortOrder);

        TypedQuery<Car> query = getEntityManager().createQuery(queryString, Car.class);
        if (!filter.isEmpty()) {
            query.setParameter("filter", "%" + filter + "%");
        }

        return query.setFirstResult(first).setMaxResults(pageSize).getResultList();
    }

    public int getFilteredRowCount(String filter) {
        String queryString = "SELECT COUNT(c.id) FROM Car c";
        queryString += filter.isEmpty() ? "" : getFilteredQueryString();

        Query query = getEntityManager().createQuery(queryString);
        if (!filter.isEmpty()) {
            query.setParameter("filter", "%" + filter + "%");
        }

        return (int) (long) query.getSingleResult();
    }
    
    private String getFilteredQueryString() {
        return " WHERE c.licencePlate LIKE :filter";
    }
}
