package web.beans;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import main.domain.Invoice;
import main.service.InvoiceService;
import web.core.helpers.FrontendHelper;
import web.model.DataTableModel;

/**
 * @author Sam
 */
@Named
@ViewScoped
public class InvoiceOverviewBean extends DataTableModel<InvoiceService, Invoice> implements Serializable {

    @Inject
    private InvoiceService invoiceService;

    private Invoice invoice;

    /**
     * Save a invoice to the database and display a success message when finished.
     */
    public void save() {
        invoiceService.update(invoice);
        FrontendHelper.displaySuccessSmallBox("Saved");
    }

    @Override
    protected InvoiceService getService() {
        return invoiceService;
    }

    @Override
    protected List<Invoice> getData() {
        return invoiceService.getSortedFilteredAndPaged(getStartIndex(), getItemsPerPage(), getSortedOn(), getSortOrder(), getFilter());
    }

    @Override
    protected int getRowCount() {
        return invoiceService.getFilteredRowCount(getFilter());
    }

    @Override
    protected String getDefaultSort() {
        return "i.id";
    }

    public void sortByPaymentStatus() {
        sort("i.paymentStatus");
    }

    public void sortByDriver() {
        sort("o.driver.firstName");
    }

    public void sortById() {
        sort("i.id");
    }

    public void sortByPeriod() {
        sort("i.period");
    }

    public void sortByTotalAmount() {
        sort("i.totalAmount");
    }

    //<editor-fold defaultstate="collapsed" desc="Getters/Setters">
    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }
    //</editor-fold>
}
