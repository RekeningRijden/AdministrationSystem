package tests.entity;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.Date;

import main.domain.Invoice;
import main.domain.Ownership;
import main.domain.enums.PaymentStatus;

import static org.junit.Assert.assertEquals;

/**
 * @author Sam
 */
public class InvoiceTest {

    @Test
    public void testGettersSetters() {
        Ownership ownership = new Ownership();
        ownership.setId(4L);

        Date date = new Date();
        BigDecimal bd = BigDecimal.TEN;

        Invoice invoice = new Invoice();
        invoice.setFilePath("c://path");
        invoice.setId(1L);
        invoice.setOwnership(ownership);
        invoice.setPaymentStatus(PaymentStatus.OPEN);
        invoice.setPeriod(date);
        invoice.setTotalAmount(bd);

        assertEquals("c://path", invoice.getFilePath());
        assertEquals(new Long(1L), invoice.getId());
        assertEquals(ownership.getId(), invoice.getOwnership().getId());
        assertEquals(PaymentStatus.OPEN, invoice.getPaymentStatus());
        assertEquals(date.getTime(), invoice.getPeriod().getTime());
        assertEquals(bd, invoice.getTotalAmount());
    }
}
