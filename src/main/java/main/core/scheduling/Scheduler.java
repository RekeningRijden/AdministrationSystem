package main.core.scheduling;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import main.core.communcation.Communicator;
import main.domain.simulation.CarTracker;

/**
 * @author Sam
 */
public class Scheduler {

    public Scheduler() {

    }

    public void start() {
        try {
            List<CarTracker> trackers = Communicator.getAllCartrackers();










        } catch (IOException e) {
            Logger.getLogger(Communicator.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
