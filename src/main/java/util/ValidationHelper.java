package util;

import java.util.regex.Pattern;

import main.domain.Driver;
import main.domain.Invoice;

/**
 * @author Marijn
 */
public final class ValidationHelper {

    private ValidationHelper(){
        //Utility class constructor cannot be called.
    }

    /**
     * Try to parse a String to and integer and check if the resulting integer is positive (zero or bigger).
     *
     * @param s to parse.
     * @return true if the String is not null or empty and could be parsed and the resulting integer is positive.
     */
    public static boolean isPositiveInteger(String s) {
        if (stringIsNullOrEmpty(s)) {
            return false;
        }

        int value;
        try {
            value = Integer.parseInt(s);
        } catch (NullPointerException | NumberFormatException e) {
            return false;
        }

        return isPositiveInteger(value);
    }

    /**
     * Check if the given integer is positive (zero or bigger).
     *
     * @param i to check.
     * @return true if the integer is positive;
     */
    public static boolean isPositiveInteger(int i) {
        return i >= 0;
    }

    /**
     * Check if the given long is positive (zero or bigger).
     *
     * @param l to check.
     * @return true if the long is positive;
     */
    public static boolean isPositiveLong(Long l) {
        return l >= 0L;
    }

    /**
     * Check if the given String is null or empty.
     *
     * @param value to check.
     * @return true if the String is null or empty.
     */
    public static boolean stringIsNullOrEmpty(String value) {
        return value == null || value.isEmpty();
    }

    /**
     * Check if a given String matches the regex of a Portuguese licencePlate.
     *
     * @param licencePlate to check.
     * @return true if the String matches.
     */
    public static boolean isValidLicencePlate(String licencePlate) {
        Pattern pattern = Pattern.compile("((?:[A-Z]{2}-\\d{2}-\\d{2})|(?:\\d{2}-[A-Z]{2}-\\d{2})|(?:\\d{2}-\\d{2}-[A-Z]{2}))");
        return pattern.matcher(licencePlate).matches();
    }

    /**
     * Check if the given Driver has all the necessary data to update or create the Driver.
     *
     * @param driver to check.
     * @return true if the Driver is not null, has an id and the id is positive.
     */
    public static boolean isValidDriver(Driver driver) {
        return !(driver == null || driver.getId() == null || isPositiveLong(driver.getId()));
    }

    /**
     * Check if the given Invoice has all the necessary data to update or create the Invoice.
     *
     * @param invoice to check.
     * @return true if the Invoice is not null and has an Ownership that is not null and the Ownership has a Car that is not null.
     */
    public static boolean isValidInvoice(Invoice invoice) {
        return !(invoice == null || invoice.getOwnership() == null || invoice.getOwnership().getCar() == null);
    }
}
