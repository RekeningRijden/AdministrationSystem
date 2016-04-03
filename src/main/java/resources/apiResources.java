package resources;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import main.domain.Driver;
import main.service.DriverService;


/**
 * Created by Eric on 02-04-16.
 */

/**
 * -- LET OP --
 * User is de gebruiker van de RekeningsrijdersApplicatie (ofwel de eindgebruiker). In deze app hoort deze gebruiker bij de class Driver!!
 * De class User in deze app refereert naar een medewerker van de overheid (gebruiker dus van deze app)
 */
@Path("/users")
@Named
public class ApiResources {

    @Inject
    private DriverService driverService;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getUsers() {
        return "test";
    }

    @GET
    @Path("/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Driver getDriverById(@PathParam("userId") Long userId) {
        System.out.println("Testing...");
        return driverService.findById(userId);
    }

//    @GET
//    @Path("/{userId}/invoices")
//    @Produces(MediaType."application/json")
//    public List<Invoice>

}
