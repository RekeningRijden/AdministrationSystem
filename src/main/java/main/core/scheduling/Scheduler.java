package main.core.scheduling;

import java.io.Serializable;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.inject.Inject;

import main.core.calculation.InvoiceGenerator;

/**
 * @author Sam
 */
@Singleton
public class Scheduler implements Serializable {

    @Inject
    private InvoiceGenerator invoiceGenerator;

    /**
     * Start invoice generation on the first day of the month.
     */
    @Schedule(dayOfMonth = "1")
    public void doWork() {
        //invoiceGenerator.generate();
    }
}
