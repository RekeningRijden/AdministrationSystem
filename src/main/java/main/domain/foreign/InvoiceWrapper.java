package main.domain.foreign;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;

import javax.xml.bind.annotation.XmlTransient;

import main.domain.Invoice;

/**
 * Created by Eric on 31-05-16.
 */
public class InvoiceWrapper {


    @XmlTransient
    private Invoice invoice;

    private int cartrackerId;
    private double totalAmount;
    private String invoiceDate;

    private SimpleDateFormat sdf;

    public InvoiceWrapper(Invoice invoice, int cartrackerId) {
        this();
        this.invoice = invoice;
        this.cartrackerId = cartrackerId;
    }

    public InvoiceWrapper(){
        sdf = new SimpleDateFormat("YYYY-MM-dd");
    }

    //<editor-fold desc="Getters/Setters">
    @XmlTransient
    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public int getCartrackerId() {
        return cartrackerId;
    }

    public void setCartrackerId(int cartrackerId) {
        this.cartrackerId = cartrackerId;
    }

    public double getTotalAmount() {
        return invoice.getTotalAmount().setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getInvoiceDate() {
        return sdf.format(invoice.getPeriod());
    }

    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }
    //</editor-fold>
}
