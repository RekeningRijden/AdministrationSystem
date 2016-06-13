package main.core.exception;

/**
 * @author Sam
 *         This exception can be thrown when something goes wrong
 *         with the relation between a Car and a Driver.
 */
public class DriverAssignmentException extends Exception {

    public DriverAssignmentException(String message) {
        super(message);
    }
}
