package main.dao;

import main.domain.Ownership;
import main.service.DriverService;
import main.service.UserService;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by martijn on 30-5-2016.
 */
public abstract class OwnershipDao extends AbstractDao<Ownership> {

    @Inject
    private DriverService driverservice;

    /**
     * Gets a list of ownerships from driver with driverId
     * @param driverId The id of the driver
     * @return Null if driver with driverId does not exist, empty list if driver exists but has no ownerships, the list with ownerships if the driver exists and has ownerships.
     */
    public List<Ownership> getOwnershipsFromDriver(Long driverId) {
        if (driverservice.findById(driverId) != null) {
            List<Ownership> ownerships = getEntityManager().createQuery("SELECT o FROM Ownership o WHERE o.driver.id = :driverId", Ownership.class)
                    .setParameter("driverId", driverId)
                    .getResultList();
            return ownerships;
        }
        return null;

    }
}
