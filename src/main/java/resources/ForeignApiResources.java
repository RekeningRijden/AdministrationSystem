package resources;

import main.core.calculation.InvoiceGenerator;
import main.domain.Invoice;
import main.domain.foreign.InvoiceRequest;
import main.domain.simulation.TrackingPeriod;
import main.domain.foreign.InvoiceWrapper;
import main.service.RateService;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Collections;
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
        if (invoiceRequest.getPositions() != null && invoiceRequest.getPositions().size() > 1 && invoiceRequest.getCartrackerId() > 0 && invoiceRequest.getRate() != null && rateService.getByName(invoiceRequest.getRate().toUpperCase()) != null) {

            Collections.sort(invoiceRequest.getPositions(), (pos1, pos2) -> pos1.getDate().compareTo(pos2.getDate()));

            TrackingPeriod trackingPeriod = new TrackingPeriod(0L);
            trackingPeriod.setPositions(invoiceRequest.getPositions());
            trackingPeriod.setStartedTracking(invoiceRequest.getPositions().get(0).getDate());
            trackingPeriod.setFinishedTracking(invoiceRequest.getPositions().get(invoiceRequest.getPositions().size() - 1).getDate());
            List<TrackingPeriod> trackingPeriods = new ArrayList<>();
            trackingPeriods.add(trackingPeriod);
            Invoice invoice = invoiceGenerator.generateForeignInvoice(trackingPeriods, rateService.getByName(invoiceRequest.getRate()));
            return  new InvoiceWrapper(invoice, invoiceRequest.getCartrackerId());
        } else {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }
}
