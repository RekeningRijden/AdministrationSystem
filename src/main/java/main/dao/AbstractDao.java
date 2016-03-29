package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import domain.IEntity;
import domain.enums.SortOrder;

/**
 * Base class for all persistence database actions.
 *
 * @param <EntityType> The database entity class type used for all operations
 * @author Sam
 */
public abstract class AbstractDao<EntityType extends IEntity> {

    @PersistenceContext(name = "MovementPU")
    private EntityManager entityManager;

    /**
     * @return Entity Type for all operations.
     */
    protected abstract Class<EntityType> getEntityClass();

    protected EntityManager getEntityManager() {
        return entityManager;
    }

    public EntityType create(EntityType entity) {
        entityManager.persist(entity);
        return entity;
    }

    public EntityType update(EntityType entity) {
        return entityManager.merge(entity);
    }

    public void remove(EntityType entity) {
        entityManager.remove(entityManager.merge(entity));
    }

    public void flush() {
        entityManager.flush();
    }

    public int count() {
        return entityManager.createNamedQuery(getEntityClass() + ".count", getEntityClass()).getResultList().size();
    }

    public EntityType findById(Object id) {
        return entityManager.find(getEntityClass(), id);
    }

    public boolean hasBeenPersisted(EntityType entity) {
        return entity.getId() != null;
    }

    /**
     * @return all results from a Entity Type table.
     */
    public List<EntityType> getAll() {
        CriteriaBuilder qb = entityManager.getCriteriaBuilder();
        CriteriaQuery<EntityType> c = qb.createQuery(getEntityClass());
        c.from(getEntityClass());

        TypedQuery<EntityType> query = entityManager.createQuery(c);
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
    protected EntityType oneResult(TypedQuery<EntityType> q) {
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
