package main.dao;

import main.domain.Invoice;

import java.util.List;

/**
 * Created by Eric on 03-04-16.
 */
public abstract class InvoiceDao extends AbstractDao {

    /**
     * Get all invoices belonging to a cartracker
     * @param cartrackerId The id of the cartracker
     * @return All invoices belonging to a cartracker
     */
    public List<Invoice> getInvoicesForCartrackerWithId(Long cartrackerId) {
        return getEntityManager().createNamedQuery("findAllInvoicesForUserWithId").setParameter("cartrackerId", cartrackerId).getResultList();
    }
}
