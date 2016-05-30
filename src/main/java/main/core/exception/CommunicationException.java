package main.core.exception;

import java.io.IOException;

/**
 * @author Sam
 */
public class CommunicationException extends IOException {

    public CommunicationException(String message) {
        super(message);
    }
}
