package main.dao;

import main.domain.Ownership;

import java.util.List;

/**
 * Created by martijn on 30-5-2016.
 */
public abstract class OwnershipDao extends AbstractDao<Ownership> {

    public List<Ownership> getOwnershipsFromDriver(Long driverId) {
        return getEntityManager().createQuery("SELECT o FROM Ownership o WHERE o.driver.id = :driverId", Ownership.class)
                .setParameter("driverId", driverId)
                .getResultList();
    }
}
