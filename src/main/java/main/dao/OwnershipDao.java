package main.dao;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import main.domain.Ownership;
import main.service.DriverService;

/**
 * Created by martijn on 30-5-2016.
 */
public abstract class OwnershipDao extends AbstractDao<Ownership> {

    @Inject
    private DriverService driverservice;

    /**
     * Gets a list of ownerships from driver with driverId
     *
     * @param driverId The id of the driver
     * @return Null if driver with driverId does not exist, empty list if driver exists but has no ownerships, the list with ownerships if the driver exists and has ownerships.
     */
    public List<Ownership> getOwnershipsFromDriver(Long driverId) {
        if (driverservice.findById(driverId) != null) {
            return getEntityManager().createQuery("SELECT o FROM Ownership o WHERE o.driver.id = :driverId", Ownership.class)
                    .setParameter("driverId", driverId)
                    .getResultList();
        }

        return new ArrayList<>();
    }
}
