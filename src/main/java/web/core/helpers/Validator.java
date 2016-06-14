package web.core.helpers;

import main.domain.Address;
import main.domain.Driver;

/**
 * @author Sam
 */
public final class Validator {

    private Validator() {
        //Utility class constructor cannot be called
    }

    public static boolean validDriver(Driver driver) {
        Address address = driver.getAddress();

        return !driver.getFirstName().isEmpty()
                && !driver.getLastName().isEmpty()
                && !driver.getEmail().isEmpty()
                && !address.getStreet().isEmpty()
                && !address.getStreetNr().isEmpty()
                && !address.getZipCode().isEmpty()
                && !address.getCountry().isEmpty()
                && !address.getCity().isEmpty();
    }
}
