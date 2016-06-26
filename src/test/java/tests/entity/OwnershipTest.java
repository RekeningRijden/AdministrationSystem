package tests.entity;

import org.junit.Test;

import java.util.Date;

import main.domain.Car;
import main.domain.Driver;
import main.domain.Invoice;
import main.domain.Ownership;

import static org.junit.Assert.assertEquals;

/**
 * @author Sam
 */
public class OwnershipTest {

    @Test
    public void testGettersSetters() {
        Invoice invoice = new Invoice();
        invoice.setId(3L);

        Car car = new Car();
        car.setId(5L);

        Driver driver = new Driver();
        driver.setId(6L);

        Date end = new Date();
        Date start = new Date();

        Ownership ownership = new Ownership();
        ownership.setCar(car);
        ownership.setDriver(driver);
        ownership.setEndDate(end);
        ownership.setId(1L);
        ownership.getInvoices().add(invoice);
        ownership.setStartDate(start);

        assertEquals(car.getId(), ownership.getCar().getId());
        assertEquals(driver.getId(), ownership.getDriver().getId());
        assertEquals(end.getTime(), ownership.getEndDate().getTime());
        assertEquals(new Long(1L), ownership.getId());
        assertEquals(invoice.getId(), ownership.getInvoices().get(0).getId());
        assertEquals(start.getTime(), ownership.getStartDate().getTime());
    }
}
