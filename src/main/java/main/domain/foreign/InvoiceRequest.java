package main.domain.foreign;

import main.domain.simulation.Position;

import java.util.List;

/**
 * Created by Eric on 31-05-16.
 */
public class InvoiceRequest {

    private int cartrackerId;

    private List<Position> positions;

    private String rate;

    public InvoiceRequest(){}

    //<editor-fold defaultstate="collapsed" desc="Getters/Setters">
    public int getCartrackerId() {
        return cartrackerId;
    }

    public void setCartrackerId(int cartrackerId) {
        this.cartrackerId = cartrackerId;
    }

    public List<Position> getPositions() {
        return positions;
    }

    public void setPositions(List<Position> positions) {
        this.positions = positions;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }
    //</editor-fold>
}
