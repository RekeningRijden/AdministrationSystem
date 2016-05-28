package main.service;

import java.io.Serializable;

import javax.ejb.Stateless;

import main.dao.RegionDao;
import main.domain.Region;

/**
 * @author Sam
 */
@Stateless
public class RegionService extends RegionDao implements Serializable {

    @Override
    protected Class<Region> getEntityClass() {
        return Region.class;
    }
}
