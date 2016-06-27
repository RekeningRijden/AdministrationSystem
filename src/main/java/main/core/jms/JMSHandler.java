package main.core.jms;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.domain.Address;
import main.domain.Car;
import main.domain.Driver;
import main.service.IntegrationService;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Created by Eric on 24-06-16.
 */
public class JMSHandler implements Serializable{

    private IntegrationService integrationService;

    public JMSHandler(IntegrationService integrationService) {
        this.integrationService = integrationService;
    }

    public void handleMessage(String message) {

        try {
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(message);

            String licencePlate = json.get("licencePlate").toString();
            Long cartrackerId = (Long) json.get("cartrackerId");
            Car car = new Car();
            car.setLicencePlate(licencePlate);
            car.setCartrackerId(cartrackerId);


            JSONObject jsonCurrentOwnership = (JSONObject) json.get("currentOwnership");
            JSONObject jsonDriver = (JSONObject) jsonCurrentOwnership.get("driver");
            JSONObject jsonAddress = (JSONObject) jsonDriver.get("address");
            String firstName = jsonDriver.get("firstName").toString();
            String lastName = jsonDriver.get("lastName").toString();

            String street = jsonAddress.get("street").toString();
            String streetNr = jsonAddress.get("streetNr").toString();
            String zipCode = jsonAddress.get("zipCode").toString();
            String city = jsonAddress.get("city").toString();
            String country = jsonAddress.get("country").toString();

            Address address = new Address();
            address.setStreet(street);
            address.setStreetNr(streetNr);
            address.setZipCode(zipCode);
            address.setCity(city);
            address.setCountry(country);

            Driver driver = new Driver();
            driver.setFirstName(firstName);
            driver.setLastName(lastName);
            driver.setAddress(address);

            integrationService.AddNewForeignCarWithDriverAndOwnership(car, driver);

        } catch (ParseException e) {
            Logger.getLogger(JMSHandler.class.getName()).log(Level.SEVERE, null, e);
        }

    }
}
