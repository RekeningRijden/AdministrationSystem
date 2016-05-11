package main.domain.enums;

import java.io.Serializable;

/**
 * Created by Eric on 02-04-16.
 */
public enum PaymentStatus implements Serializable{

    PAID,
    OPEN,
    CANCELLED;
}
