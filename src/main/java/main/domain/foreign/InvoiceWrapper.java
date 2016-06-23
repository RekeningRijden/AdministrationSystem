package main.domain.foreign;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;

import javax.xml.bind.annotation.XmlTransient;

import com.google.gson.annotations.Expose;
import main.domain.Invoice;

/**
 * Created by Eric on 31-05-16.
 */
public class InvoiceWrapper {

    @Expose
    private Invoice invoice;

    @Expose
    private String country;

    public InvoiceWrapper(Invoice invoice, String country) {
        this.invoice = invoice;
        this.country = country;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
