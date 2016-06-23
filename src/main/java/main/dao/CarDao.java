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

import java.util.HashMap;
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

    private static String getFilteredQueryString() {
        return " WHERE c.licencePlate LIKE :filter OR d.lastName LIKE :filter";
    }

    public List<Car> getCarsFromDriverWithId(Long driverId) {
        return getEntityManager().createNamedQuery("findAllCarsFromDriverWithId", Car.class)
                .setParameter("driverId", driverId)
                .getResultList();
    }

    public Car getCarByCartrackerId(Long cartrackerId) {
        TypedQuery<Car> q = getEntityManager().createQuery("SELECT c FROM Car c WHERE c.cartrackerId = :cartrackerId", Car.class)
                .setParameter("cartrackerId", cartrackerId);

        return oneResult(q);
    }

    /**
     * Find the Car object in the database which has the given licencePlate.
     * @param licencePlate to look for.
     * @return a Car object.
     */
    public Car getCarByLicencePlate(String licencePlate){
        TypedQuery<Car> q = getEntityManager().createQuery("SELECT c FROM Car c WHERE c.licencePlate = :licencePlate", Car.class)
                .setParameter("licencePlate", licencePlate);

        return oneResult(q);
    }

    public HashMap<Long, String> getCountriesForForeignCartrackers() {
        HashMap<Long, String> result = new HashMap<>();
        Query query = getEntityManager().createQuery("select c.cartrackerId, a.country from Car c left join c.currentOwnership.driver.address a where c.cartrackerId < 0");
        List<Object[]> strings = query.getResultList();
        for(Object[] obj : strings) {
            result.put((Long) obj[0], (String) obj[1]);

        }
        return result;
    }
}
