package main.core.calculation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.domain.Region;
import main.domain.simulation.Position;
import main.domain.simulation.TrackingPeriod;

/**
 * @author Sam
 */
public final class Calculator {

    private static final double EARTH_RADIUS = 6371.8;

    private Calculator() {
        //Utility class constructor cannot be called.
    }

    /**
     * Calculate the total distance travelled by a Car in one @{code TrackingPeriod}.
     *
     * @param trackingPeriod to calculate the total traveled distance from.
     * @param regions        where the positions could be in.
     * @return distance in kilometres per Region.
     */
    public static Map<Region, Double> calculateTotalDistance(TrackingPeriod trackingPeriod, List<Region> regions) {
        Map<Region, Double> distances = new HashMap<>();

        List<Position> positions = trackingPeriod.getPositions();
        for (int i = 0; i < positions.size() - 1; i++) {
            Position posOne = positions.get(i);
            Position posTwo = positions.get(i + 1);

            Region region = findRegion(posOne, regions);
            if (distances.containsKey(region)) {
                double currentDistance = distances.get(region);
                distances.put(region, currentDistance + calculateDistanceBetweenPositions(posOne, posTwo));
            } else {
                distances.put(region, calculateDistanceBetweenPositions(posOne, posTwo));
            }
        }

        return distances;
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

    /**
     * Find the Region the given Position is in.
     *
     * @param position to check for
     * @param regions  to choose from while finding the correct Region.
     * @return the Region the position is in.
     */
    private static Region findRegion(Position position, List<Region> regions) {
        double latitude = position.getLatitude();

        for (Region region : regions) {
            if (region.getLatitudeSouth() < latitude) {
                return region;
            }
        }

        return null;
    }
}
