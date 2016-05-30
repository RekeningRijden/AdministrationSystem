package main.service;

import main.dao.OwnershipDao;
import main.domain.Ownership;

import javax.ejb.Stateless;
import java.io.Serializable;

/**
 * Created by martijn on 30-5-2016.
 */
@Stateless
public class OwnershipService extends OwnershipDao implements Serializable {

    @Override
    protected Class<Ownership> getEntityClass() {
        return Ownership.class;
    }
}
