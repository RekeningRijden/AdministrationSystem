package main.dao;

import main.domain.Invoice;
import main.domain.enums.SortOrder;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 * Created by Eric on 03-04-16.
 */
public abstract class InvoiceDao extends AbstractDao<Invoice> {

    /**
     * Get all invoices belonging to a cartracker
     *
     * @param cartrackerId The id of the cartracker
     * @return All invoices belonging to a cartracker
     */
    public List<Invoice> getInvoicesForCartrackerWithId(Long cartrackerId) {
        return getEntityManager().createNamedQuery("findAllInvoicesForUserWithId", Invoice.class)
                .setParameter("cartrackerId", cartrackerId)
                .getResultList();
    }

    public List<Invoice> getSortedFilteredAndPaged(int first, int pageSize,
                                                   String sortValue, SortOrder sortOrder, String filter) {

        String queryString = "SELECT i FROM Invoice i LEFT JOIN i.ownership o";
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
        return " WHERE CAST(i.period CHAR(255)) LIKE :filter " +
                "OR CAST(i.paymentStatus CHAR(255)) LIKE :filter " +
                "OR (concat(o.driver.firstName, ' ', o.driver.lastName)) LIKE :filter " +
                "OR CAST(i.totalAmount CHAR(255)) LIKE :filter";
    }
}
