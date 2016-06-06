package main.domain.simulation;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Class keeping data about a certain period of a carTracker.
 */
public class TrackingPeriod implements Serializable {

    private Long serialNumber;

    @Expose
    private Date startedTracking;
    @Expose
    private Date finishedTracking;
    @Expose
    private List<Position> positions;

    public TrackingPeriod(Long serialNumber) {
        this.serialNumber = serialNumber;
        this.startedTracking = new Date();
        this.positions = new ArrayList<>();
    }

    /**
     * Add a new (latest) position.
     *
     * @param position to add.
     */
    public void addPosition(Position position) {
        positions.add(position);
    }

    /**
     * @return the last position the carTracker was at in this trackingPeriod.
     */
    public Position getLastPosition() {
        return positions.get(positions.size() - 1);
    }

    /**
     * Set finishedTracking date to the current date.
     */
    public void finishTracking() {
        finishedTracking = new Date();
    }

    //<editor-fold desc="Getters/Setters">
    public Long getSerialNumber() {
        return serialNumber;
    }

    public Date getStartedTracking() {
        return startedTracking;
    }

    public Date getFinishedTracking() {
        return finishedTracking;
    }

    public List<Position> getPositions() {
        return positions;
    }

    public void setPositions(List<Position> positions) {
        this.positions = positions;
    }

    public void setStartedTracking(Date startedTracking) {
        this.startedTracking = startedTracking;
    }

    public void setFinishedTracking(Date finishedTracking) {
        this.finishedTracking = finishedTracking;
    }

    //</editor-fold>
}
