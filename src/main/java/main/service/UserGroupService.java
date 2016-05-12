package main.service;

import main.dao.UserGroupDao;
import main.domain.UserGroup;

import javax.ejb.Stateless;
import java.io.Serializable;

/**
 * Created by martijn on 12-5-2016.
 */
@Stateless
public class UserGroupService extends UserGroupDao implements Serializable {

    @Override
    protected Class<UserGroup> getEntityClass() {
        return UserGroup.class;
    }

}
