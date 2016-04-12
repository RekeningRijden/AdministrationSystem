package main.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import main.dao.InvoiceDao;
import main.domain.Car;
import main.domain.Invoice;
import main.domain.enums.SortOrder;

/**
 * Created by Eric on 03-04-16.
 */

@Stateless
public class InvoiceService extends InvoiceDao implements Serializable {

    @Inject
    private CarService carService;

    @Override
    protected Class<Invoice> getEntityClass() {
        return Invoice.class;
    }

    /**
     * Gets all invoices from all cars of a driver
     *
     * @param driverId The id of the driver
     * @return All invoices for a driver
     */
    public List<Invoice> getInvoicesFromDriverWithId(Long driverId) {
        List<Invoice> invoices = new ArrayList<>();

        List<Car> cars = carService.getCarsFromDriverWithId(driverId);

        for (Car car : cars) {
            invoices.addAll(getInvoicesForCartrackerWithId(car.getCartrackerId()));
        }

        return invoices;
    }

    public List<Invoice> getSortedFilteredAndPaged(int first, int pageSize,
                                               String sortValue, SortOrder sortOrder, String filter) {

        String queryString = "SELECT i FROM Invoice i";
        queryString += filter.isEmpty() ? "" : getFilteredQueryString();
        queryString += sortValue.isEmpty() ? "" : getSortedQueryString(sortValue, sortOrder);

        TypedQuery<Invoice> query = getEntityManager().createQuery(queryString, Invoice.class);
        if (!filter.isEmpty()) {
            query.setParameter("filter", "%" + filter + "%");
        }

        return query.setFirstResult(first).setMaxResults(pageSize).getResultList();
    }

    public int getFilteredRowCount(String filter) {
        String queryString = "SELECT COUNT(i.id) FROM Invoice i LEFT JOIN i.ownership o";
        queryString += filter.isEmpty() ? "" : getFilteredQueryString();

        Query query = getEntityManager().createQuery(queryString);
        if (!filter.isEmpty()) {
            query.setParameter("filter", "%" + filter + "%");
        }

        return (int) (long) query.getSingleResult();
    }

    private String getFilteredQueryString() {
        return " WHERE i.paymentStatus LIKE :filter OR CAST(i.period CHAR(255)) LIKE :filter OR (concat(o.driver.firstName, ' ', o.driver.lastName) LIKE :filter";
    }
}
