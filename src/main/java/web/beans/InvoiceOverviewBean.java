package web.beans;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import main.domain.Invoice;
import main.service.InvoiceService;
import web.model.DataTableModel;

/**
 * @author Sam
 */
@Named
@SessionScoped
public class InvoiceOverviewBean extends DataTableModel<InvoiceService, Invoice> implements Serializable {

    @Inject
    private InvoiceService invoiceService;

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

    public void sortByPaymentStatus(){
        sort("i.paymentStatus");
    }

    public void sortByDriver(){
        sort("o.driver.firstName");
    }

    public void sortById(){
        sort("i.id");
    }

    public void sortByPeriod(){
        sort("i.period");
    }

    public void sortByTotalAmount(){
        sort("i.totalAmount");
    }
}
