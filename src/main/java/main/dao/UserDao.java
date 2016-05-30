/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.dao;

import main.domain.User;

import javax.persistence.TypedQuery;

/**
 * @author Sam
 */
public abstract class UserDao extends AbstractDao<User> {

    public User login(String username, String password) {

        TypedQuery<User> q = getEntityManager().createQuery("SELECT u FROM User u WHERE u.username = :username AND u.password = :password", User.class);
        q.setParameter("username", username);
        q.setParameter("password", password);

        return q.getResultList().isEmpty() ? null : q.getResultList().get(0);

    }

}
