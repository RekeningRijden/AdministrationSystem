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
}
