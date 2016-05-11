package main.domain.enums;

import java.io.Serializable;

/**
 * Created by Eric on 02-04-16.
 */
public enum PaymentStatus implements Serializable{

    PAID("Paid"),
    OPEN("Open"),
    CANCELLED("Cancelled");
    
    private final String name;

    private PaymentStatus(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
