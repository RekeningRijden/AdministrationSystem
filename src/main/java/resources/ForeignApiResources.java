package resources;

import main.core.calculation.InvoiceGenerator;
import main.domain.Invoice;
import main.domain.foreign.InvoiceRequest;
import main.domain.simulation.Position;
import main.domain.simulation.TrackingPeriod;
import main.domain.foreign.InvoiceWrapper;
import main.service.RateService;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Eric on 31-05-16.
 */
@Path("/foreign")
@Named
public class ForeignApiResources {
    @Inject
    private InvoiceGenerator invoiceGenerator;

    @Inject
    private RateService rateService;

    @POST
    @Path("/invoice")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public InvoiceWrapper calculateForeignInvoice(InvoiceRequest invoiceRequest) {
         Collections.sort(invoiceRequest.getPositions(), new Comparator<Position>(){
            public int compare(Position pos1, Position pos2){
                return pos1.getDate().compareTo(pos2.getDate());
            }
        });

        TrackingPeriod trackingPeriod = new TrackingPeriod(0L);
        trackingPeriod.setPositions(invoiceRequest.getPositions());
        trackingPeriod.setStartedTracking(invoiceRequest.getPositions().get(0).getDate());
        trackingPeriod.setFinishedTracking(invoiceRequest.getPositions().get(invoiceRequest.getPositions().size()-1).getDate());
        List<TrackingPeriod> trackingPeriods = new ArrayList();
        trackingPeriods.add(trackingPeriod);
        Invoice invoice = invoiceGenerator.generateForeignInvoice(trackingPeriods, rateService.getByName(invoiceRequest.getRate()));
        InvoiceWrapper invoicewrapper = new InvoiceWrapper(invoice, invoiceRequest.getCartrackerId());
        return invoicewrapper;
    }
}
