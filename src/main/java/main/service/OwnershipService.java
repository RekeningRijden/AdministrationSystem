package main.service;

import javax.ejb.Stateless;

import main.dao.OwnershipDao;
import main.domain.Ownership;

/**
 * @author Sam
 */
@Stateless
public class OwnershipService extends OwnershipDao {
    @Override
    protected Class<Ownership> getEntityClass() {
        return Ownership.class;
    }
}
