package resources;

import javax.ws.rs.*;

import main.domain.Car;
import main.domain.Driver;
import main.domain.Invoice;
import main.service.CarService;
import main.service.DriverService;
import main.service.InvoiceService;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.List;

import main.domain.Ownership;
import main.service.OwnershipService;
import pagination.DriverPagination;
import pagination.InvoicePagination;
import util.ValidationHelper;

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
public class ApiResourcesV1 {

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
    public DriverPagination getDrivers(@QueryParam("pageIndex") String pageIndex, @QueryParam("pageSize") String pageSize) {
        DriverPagination resultSet = new DriverPagination();
        if (ValidationHelper.isPositiveInteger(pageIndex)) {
            resultSet.setPageIndex(Integer.parseInt(pageIndex));
        } else {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        if (ValidationHelper.isPositiveInteger(pageSize)) {
            resultSet.setPageSize(Integer.parseInt(pageSize));
        } else {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }

        resultSet.setTotalCount(driverService.count());
        List<Driver> drivers = driverService.getAllPaginated(resultSet.getPageIndex(), resultSet.getPageSize());
        resultSet.setItems(drivers);
        return resultSet;
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
        if (!ValidationHelper.isValidDriver(driver)) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }

        return driverService.create(driver);
    }

    /**
     * update driver
     *
     * @param driverId  The id of the driver
     * @param newDriver The updated driver to persist
     * @return The updated created Driver
     */
    @POST
    @Path("/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Driver updateDriver(@PathParam("userId") Long driverId, Driver newDriver) {
        if (!ValidationHelper.isPositiveLong(driverId) || !ValidationHelper.isValidDriver(newDriver)) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }

        Driver driver = driverService.findAndSetAddress(driverId, newDriver);
        if (driver == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }

        return driver;
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
        if (!ValidationHelper.isPositiveLong(driverId)) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }

        Driver driver = driverService.findById(driverId);
        if (driver == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return driver;
    }

    /**
     * Gets all invoices from a driver based on his id
     *
     * @param driverId  The id of the driver
     * @param pageIndex selected page
     * @param pageSize  amount of items
     * @return The invoices belonging to the driver
     */
    @GET
    @Path("/{userId}/invoices")
    @Produces(MediaType.APPLICATION_JSON)
    public InvoicePagination getInvoicesForUserWithId(@PathParam("userId") Long driverId, @QueryParam("pageIndex") String pageIndex, @QueryParam("pageSize") String pageSize) {
        if (!ValidationHelper.isPositiveLong(driverId)) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }

        InvoicePagination resultSet = new InvoicePagination();
        if (ValidationHelper.isPositiveInteger(pageIndex)) {
            resultSet.setPageIndex(Integer.parseInt(pageIndex));
        } else {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        if (ValidationHelper.isPositiveInteger(pageSize)) {
            resultSet.setPageSize(Integer.parseInt(pageSize));
        } else {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }

        resultSet.setTotalCount(driverService.count());
        List<Invoice> invoices = invoiceService.getInvoicesFromDriverWithId(driverId, resultSet.getPageIndex(), resultSet.getPageSize());
        resultSet.setItems(invoices);
        return resultSet;
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
        if (!ValidationHelper.isPositiveLong(invoiceId)) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }

        Invoice invoice = invoiceService.findById(invoiceId);
        if (invoice == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return invoice;
    }

    /**
     * Updates the PaymentStatus for a specific invoice
     *
     * @param invoiceId The id of the invoice
     * @param userId    The id of the user
     * @return The invoice with the updated status
     */
    @PUT
    @Path("/{userId}/invoices/{invoiceId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Invoice updateInvoicePaymentStatus(@PathParam("userId") Long userId, @PathParam("invoiceId") Long invoiceId, Invoice inv) {
        if (!ValidationHelper.isPositiveLong(invoiceId) || !ValidationHelper.isValidInvoice(inv)) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }

        Invoice invoice = invoiceService.updatePaymentStatus(invoiceId, inv.getPaymentStatus());
        if (invoice == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }

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
        if (!ValidationHelper.isValidLicencePlate(licencePlate)) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }

        Car car = carService.getCarByLicencePlate(licencePlate);
        if (car == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }

        return car;
    }

    /**
     * Get ownerships by licencePlate
     *
     * @param licencePlate The licenceplate of the car
     * @return The list with ownerships of the car with the given licenceplate
     */
    @GET
    @Path("/cars/{licencePlate}/ownerships")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Ownership> getOwnershipsFromCarWithLicencePlate(@PathParam("licencePlate") String licencePlate) {
        if (!ValidationHelper.isValidLicencePlate(licencePlate)) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }

        Car car = carService.getCarByLicencePlate(licencePlate);
        if (car == null || car.getPastOwnerships() == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }

        return car.getPastOwnerships();
    }

    /**
     * Gets the cartracker id by invoice
     *
     * @param userId    The unique id of the user
     * @param invoiceId The unique id of the invoice
     * @return the cartrackerId
     */
    @GET
    @Path("/{userId}/invoices/{invoiceId}/cartracker")
    @Produces(MediaType.APPLICATION_JSON)
    public Car getCarByInvoice(@PathParam("userId") Long userId, @PathParam("invoiceId") Long invoiceId) {
        if (!ValidationHelper.isPositiveLong(invoiceId)) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }

        Invoice invoice = invoiceService.findById(invoiceId);
        if (!ValidationHelper.isValidInvoice(invoice)) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }

        return invoice.getOwnership().getCar();
    }

    /**
     * Returns ownerships from user with id: userid
     *
     * @param userId The unique id of the user
     * @return Returns a list of ownerships if the user exists, empty list if there are no ownerships but the users exists or throws an exception when the user does not exist.
     */
    @GET
    @Path("/{userId}/ownerships")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Ownership> getOwnershipsByDriver(@PathParam("userId") Long userId) {
        if (!ValidationHelper.isPositiveLong(userId)) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }

        List<Ownership> ownerships = ownershipService.getOwnershipsFromDriver(userId);
        if (ownerships == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }

        return ownerships;
    }
}
