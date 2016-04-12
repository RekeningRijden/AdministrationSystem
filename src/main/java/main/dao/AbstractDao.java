package main.dao;

import main.domain.IEntity;
import main.domain.enums.SortOrder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import java.util.List;

/**
 * Base class for all persistence database actions.
 *
 * @param <T> The database entity class type used for all operations
 * @author Sam
 */
public abstract class AbstractDao<T extends IEntity> {

    @PersistenceContext(name = "AdministrationPu")
    private EntityManager entityManager;

    /**
     * @return Entity Type for all operations.
     */
    protected abstract Class<T> getEntityClass();

    protected EntityManager getEntityManager() {
        return entityManager;
    }

    public T create(T entity) {
        entityManager.persist(entity);
        return entity;
    }

    public T update(T entity) {
        return entityManager.merge(entity);
    }

    public void remove(T entity) {
        entityManager.remove(entityManager.merge(entity));
    }

    public void flush() {
        entityManager.flush();
    }

    public int count() {
        return entityManager.createNamedQuery(getEntityClass() + ".count", getEntityClass()).getResultList().size();
    }

    public T findById(Object id) {
        return entityManager.find(getEntityClass(), id);
    }

    public boolean hasBeenPersisted(T entity) {
        return entity.getId() != null;
    }

    /**
     * @return all results from a Entity Type table.
     */
    public List<T> getAll() {
        CriteriaBuilder qb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> c = qb.createQuery(getEntityClass());
        c.from(getEntityClass());

        TypedQuery<T> query = entityManager.createQuery(c);
        return query.getResultList();
    }

    /**
     * Generate the part of a query responsible for sorting.
     *
     * @param sortValue the database column name to sort on.
     * @param sortOrder The order to sort in.
     * @return part of a JPQL query responsible for sorting.
     */
    protected String getSortedQueryString(String sortValue, SortOrder sortOrder) {
        String queryString = " ORDER BY CAST(" + makeFieldNameJqplSafe(sortValue) + " CHAR(255))";
        queryString += (sortOrder == SortOrder.ASCENDING) ? " asc" : " desc";
        return queryString;
    }

    /**
     * @param q the TypedQuery to get the result from.
     * @return one result from a executed TypedQuery. This method avoids
     * possible NoResultExceptions being thrown.
     */
    protected T oneResult(TypedQuery<T> q) {
        q.setMaxResults(1);
        return q.getResultList().isEmpty()
                ? null : q.getResultList().get(0);
    }

    /**
     * Remove all characters not allowed in a JPQL query from a String.
     *
     * @param fieldName to remove the characters from.
     * @return String left after character replacement.
     */
    protected String makeFieldNameJqplSafe(String fieldName) {
        return fieldName.replaceAll("[^0-9a-zA-Z_\\.]", "");
    }
}
