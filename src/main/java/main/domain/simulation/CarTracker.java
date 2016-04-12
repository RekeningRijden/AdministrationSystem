package main.domain.simulation;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Sam
 */
public class CarTracker implements Serializable {

    private Long id;
    @Expose
    private String authorisationCode;

    private List<TrackingPeriod> trackingPeriods;

    public void CarTracker() {
        this.trackingPeriods = new ArrayList<>();
    }

    /**
     * Start a new trackingPeriod.
     */
    public void startTrackingPeriod() {
        trackingPeriods.add(new TrackingPeriod((long) trackingPeriods.size()));
    }

    /**
     * Finish the current trackingPeriod.
     */
    public void finishTrackingPeriod() {
        getCurrentTrackingPeriod().finishTracking();
    }

    /**
     * @return the current trackingPeriod.
     */
    public TrackingPeriod getCurrentTrackingPeriod() {
        return trackingPeriods.get((trackingPeriods.size() - 1));
    }

    public Position getLastPosition() {
        if (getCurrentTrackingPeriod().getPositions().isEmpty()) {
            return trackingPeriods.get((trackingPeriods.size() - 2)).getLastPosition();
        } else {
            return getCurrentTrackingPeriod().getLastPosition();
        }
    }

    //<editor-fold desc="Getters/Setters">
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthorisationCode() {
        return authorisationCode;
    }

    public void setAuthorisationCode(String authorisationCode) {
        this.authorisationCode = authorisationCode;
    }

    public List<TrackingPeriod> getTrackingPeriods() {
        return trackingPeriods;
    }

    //</editor-fold>
}
