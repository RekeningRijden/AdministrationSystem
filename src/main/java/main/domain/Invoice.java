package main.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import main.domain.enums.PaymentStatus;

/**
 * Created by Eric on 02-04-16.
 */
@Entity
@NamedQuery(name = "findAllInvoicesForUserWithId", query = "SELECT i FROM Invoice i WHERE i.cartrackerId = :cartrackerId")
public class Invoice implements Serializable, IEntity {

    @Id
    private Long id;

    private Long cartrackerId;
    private PaymentStatus paymentStatus;

    @Temporal(value = TemporalType.DATE)
    private Date period;
    private BigDecimal totalAmount;

    public Invoice() {
        this.paymentStatus = PaymentStatus.OPEN;
    }

    //<editor-fold defaultstate="collapsed" desc="Getters/Setters">

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCartrackerId() {
        return cartrackerId;
    }

    public void setCartrackerId(Long cartrackerId) {
        this.cartrackerId = cartrackerId;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Date getPeriod() {
        return period;
    }

    public void setPeriod(Date period) {
        this.period = period;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    //</editor-fold>
}
