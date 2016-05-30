package main.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import main.core.helper.NumberHelper;

/**
 * @author Sam
 */
@Entity
@Table(name = "Region")
public class Region implements Serializable, IEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private double latitudeSouth;

    @Column(precision = 20, scale = 10)
    private BigDecimal roadTaxPerKm;

    public Region() {
    }

    public Region(String name, double latitudeSouth, BigDecimal roadTaxPerKm) {
        this.name = name;
        this.latitudeSouth = latitudeSouth;
        this.roadTaxPerKm = roadTaxPerKm;
    }

    //<editor-fold defaultstate="collapsed" desc="Getters/Setters">

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitudeSouth() {
        return latitudeSouth;
    }

    public void setLatitudeSouth(double latitudeSouth) {
        this.latitudeSouth = latitudeSouth;
    }

    public BigDecimal getRoadTaxPerKm() {
        return roadTaxPerKm;
    }

    public void setRoadTaxPerKm(BigDecimal roadTaxPerKm) {
        this.roadTaxPerKm = roadTaxPerKm;
    }

    public String getRoadTaxPerKmString() {
        return NumberHelper.parseToString(roadTaxPerKm);
    }

    public void setRoadTaxPerKmString(String roadTaxPerKm) {
        this.roadTaxPerKm = NumberHelper.parseToBigDecimal(roadTaxPerKm);
    }

    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="HashCode/Equals">

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Region other = (Region) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    //</editor-fold>
}
