package main.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Sam
 */
@Entity
@Table(name = "Driver")
public class Driver implements Serializable, IEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;

    @OneToOne
    private Address address;

    public Driver() {
        // Empty constructor for JPA
    }

    //<editor-fold defaultstate="collapsed" desc="Getters/Setters">
    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
    
    public String getFullName(){
        return firstName + " " + lastName;
    }

    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="HashCode/Equals">

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Driver driver = (Driver) o;

        return id != null ? id.equals(driver.id) : driver.id == null;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }


    //</editor-fold>
}
