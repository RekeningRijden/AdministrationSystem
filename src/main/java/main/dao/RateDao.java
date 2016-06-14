package main.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import main.domain.Rate;

/**
 * @author martijn
 */
public abstract class RateDao extends AbstractDao<Rate> {

    public Rate getByName(String name) {
        TypedQuery<Rate> q = getEntityManager().createQuery("SELECT r FROM Rate r WHERE r.name = :name", Rate.class)
                .setParameter("name", name);

        return q.getResultList().isEmpty() ? null : q.getResultList().get(0);
    }

    public Rate getByValue(String value) {
        TypedQuery<Rate> q = getEntityManager().createQuery("SELECT r FROM Rate r WHERE r.value = :value", Rate.class)
                .setParameter("value", value);

        return q.getResultList().isEmpty() ? null : q.getResultList().get(0);
    }

    @Override
    public List<Rate> getAll() {
        return getEntityManager().createQuery("SELECT r FROM Rate r ORDER BY r.name", Rate.class)
                .getResultList();
    }
}
