package resources;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import main.domain.Car;
import main.domain.Driver;
import main.domain.Invoice;
import main.domain.Ownership;
import main.service.CarService;
import main.service.DriverService;
import main.service.InvoiceService;
import main.service.OwnershipService;

/**
 * Created by Eric on 02-04-16.
 */

/**
 * -- LET OP --
 * User in de url is de gebruiker van de RekeningsrijdersApplicatie (ofwel de eindgebruiker). In deze app hoort deze gebruiker bij de class Driver!!
 * De class User in deze app refereert naar een medewerker van de overheid (gebruiker dus van deze app)
 */
@Path("/users")
@Named
public class ApiResources {

    @Inject
    private DriverService driverService;
    @Inject
    private OwnershipService ownershipService;
    @Inject
    private InvoiceService invoiceService;
    @Inject
    private CarService carService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Driver> getDrivers() {
        return driverService.getAll();
    }

    /**
     * For TESTING purposes only
     *
     * @param driver The new Driver
     * @return The newly created Driver
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Driver addNewDriver(Driver driver) {
        return driverService.create(driver);
    }
    
    /**
     * update driver
     *
     * @param driverId
     * @param driver
     * @return The updated created Driver
     */
    @POST
    @Path("/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Driver updateDriver(@PathParam("userId") Long driverId, Driver driver) {
        Driver d = driverService.findById(driverId);
        d.setAddress(driver.getAddress());
        return driverService.update(d);
    }

    /**
     * Gets a driver based on his id
     *
     * @param driverId The id of the driver
     * @return The driver with the corresponding id
     */
    @GET
    @Path("/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Driver getDriverById(@PathParam("userId") Long driverId) {
        return driverService.findById(driverId);
    }

    /**
     * Gets all invoices from a driver based on his id
     *
     * @param driverId The id of the driver
     * @return The invoices belonging to the driver
     */
    @GET
    @Path("/{userId}/invoices")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Invoice> getInvoicesForUserWithId(@PathParam("userId") Long driverId) {
        return invoiceService.getInvoicesFromDriverWithId(driverId);
    }

    /**
     * Get a invoice based on the invoiceId (This only works if every invoice has an unique id, not an unique id per user)
     *
     * @param invoiceId The id of the invoice
     * @return The invoice belonging to the invoiceId
     */
    @GET
    @Path("/{userId}/invoices/{invoiceId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Invoice getInvoiceWithId(@PathParam("userId") Long userId, @PathParam("invoiceId") Long invoiceId) {
        return invoiceService.findById(invoiceId);
    }

    /**
     * Updates the PaymentStatus for a specific invoice
     *
     * @param invoiceId The id of the invoice
     * @param userId
     * @return The invoice with the updated status
     */
    @PUT
    @Path("/{userId}/invoices/{invoiceId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Invoice updateInvoicePaymentStatus(@PathParam("userId") Long userId, @PathParam("invoiceId") Long invoiceId, Invoice inv) {
        Invoice invoice = invoiceService.findById(invoiceId);
        invoice.setPaymentStatus(inv.getPaymentStatus());
        invoiceService.update(invoice);
        return invoice;
    }

    /**
     * Gets a Car based on its licencePlate
     *
     * @param licencePlate of the Car
     * @return the Car with the corresponding licencePlate
     */
    @GET
    @Path("/cars/{licencePlate}")
    @Produces(MediaType.APPLICATION_JSON)
    public Car getCarWithLicencePlate(@PathParam("licencePlate") String licencePlate) {
        return carService.getCarByLicencePlate(licencePlate);
    }
    
    /**
     * Get ownerships by licencePlate
     * @param licencePlate
     * @return 
     */
    @GET
    @Path("/cars/{licencePlate}/ownerships")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Ownership> getOwnershipsFromCarWithLicencePlate(@PathParam("licencePlate") String licencePlate) {
        return carService.getCarByLicencePlate(licencePlate).getPastOwnerships();
    }
    
    /**
     * Gets the cartracker id by invoice
     * @param userId
     * @param invoiceId
     * @return the cartrackerId
     */
    @GET
    @Path("/{userId}/invoices/{invoiceId}/cartracker")
    @Produces(MediaType.APPLICATION_JSON)
    public Car getCartrackerByInvoice(@PathParam("userId") Long userId, @PathParam("invoiceId") Long invoiceId) {
        Invoice invoice = invoiceService.findById(invoiceId);
        return invoice.getOwnership().getCar();
    }

    @GET
    @Path("/{userId}/ownerships")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Ownership> getOwnershipsByDriver(@PathParam("userId") Long userId) {
        return ownershipService.getOwnershipsFromDriver(userId);
    }
    
}
