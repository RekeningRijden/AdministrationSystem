package main.core.calculation;

import java.util.List;

import main.domain.simulation.Position;
import main.domain.simulation.TrackingPeriod;

/**
 * @author Sam
 */
public class Calculator {

    private static final double EARTH_RADIUS = 6371.8;

    /**
     * Calculate the total distance travelled by a Car in one @{code TrackingPeriod}.
     *
     * @param trackingPeriod to calculate the total traveled distance from.
     * @return distance in kilometres.
     */
    public static double calcuateTotalDistance(TrackingPeriod trackingPeriod) {
        double totalDistance = 0.0;

        List<Position> positions = trackingPeriod.getPositions();
        for (int i = 0; i < positions.size() - 1; i++) {
            Position posOne = positions.get(i);
            Position posTwo = positions.get(i + 1);

            totalDistance += calculateDistanceBetweenPositions(posOne, posTwo);
        }

        return totalDistance;
    }

    /**
     * Calculate the distance between two positions.
     *
     * @param posOne to be used as coordinates for the calculation.
     * @param posTwo to be used as coordinates for the calculation.
     * @return distance in kilometres.
     */
    private static double calculateDistanceBetweenPositions(Position posOne, Position posTwo) {
        double latOne = Math.toRadians(posOne.getLatitude());
        double latTwo = Math.toRadians(posTwo.getLatitude());

        double dLat = Math.toRadians(posTwo.getLatitude() - posOne.getLatitude());
        double dLon = Math.toRadians(posTwo.getLongitude() - posOne.getLongitude());

        double a = Math.pow(Math.sin(dLat / 2), 2)
                + Math.pow(Math.sin(dLon / 2), 2)
                * Math.cos(latOne)
                * Math.cos(latTwo);

        double c = 2 * Math.asin(Math.sqrt(a));

        return EARTH_RADIUS * c;
    }
}
