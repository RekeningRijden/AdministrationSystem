package main.domain;

import javax.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Sam
 */
@Entity
@Table(name = "Car")
@NamedQuery(name = "findAllCarsFromDriverWithId", query = "SELECT o.car FROM Ownership o WHERE o.driver.id = :driverId")
public class Car implements Serializable, IEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long cartrackerId;
    private String licencePlate;

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL)
    private List<Ownership> ownerships;

    public Car() {
        this.ownerships = new ArrayList<>();
    }

    //<editor-fold defaultstate="collapsed" desc="Getters/Setters">
    @Override
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

    public String getLicencePlate() {
        return licencePlate;
    }

    public void setLicencePlate(String licencePlate) {
        this.licencePlate = licencePlate;
    }

    public List<Ownership> getOwnerships() {
        return ownerships;
    }

    public void setOwnerships(List<Ownership> ownerships) {
        this.ownerships = ownerships;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="HashCode/Equals">

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Car car = (Car) o;

        return id != null ? id.equals(car.id) : car.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    //</editor-fold>
}
