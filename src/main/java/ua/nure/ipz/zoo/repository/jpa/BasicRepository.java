package ua.nure.ipz.zoo.repository.jpa;

import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import ua.nure.ipz.zoo.repository.Repository;
import ua.nure.ipz.zoo.util.DomainEntity;

public abstract class BasicRepository<T extends DomainEntity> implements Repository<T> {

    private static final String DOMAIN_ID = "domainId";

    @PersistenceContext()
    private EntityManager entityManager;
    private Class<T> clazz;

    public BasicRepository(Class<T> clazz) {
        this.clazz = clazz;
    }

    protected EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public Class<T> getEntityClass() {
        return clazz;
    }

    @Override
    public T load(int id) {
        return entityManager.find(clazz, id);
    }

    @Override
    public Iterable<T> loadAll() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteria = cb.createQuery(clazz);
        criteria.select(criteria.from(clazz));
        return entityManager.createQuery(criteria).getResultList();
    }

    @Override
    public void save(T obj) {
        entityManager.persist(obj);
    }

    @Override
    public void delete(T obj) {
        entityManager.remove(obj);
    }

    @Override
    public T findByDomainId(UUID domainId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteria = cb.createQuery(clazz);
        Root<T> root = criteria.from(clazz);
        criteria.select(root).where(cb.equal(root.get(DOMAIN_ID), cb.parameter(UUID.class, DOMAIN_ID)));

        TypedQuery<T> queryByDomainId = entityManager.createQuery(criteria);
        queryByDomainId.setParameter(DOMAIN_ID, domainId);
        List<T> results = queryByDomainId.getResultList();
        return getSingleResult(results);
    }

    @Override
    public List<UUID> selectAllDomainIds() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<UUID> criteria = cb.createQuery(UUID.class);
        Root<T> root = criteria.from(clazz);
        criteria.select(root.get(DOMAIN_ID));
        return entityManager.createQuery(criteria).getResultList();
    }

    protected T getSingleResult(List<T> results) {
        return results.size() == 1 ? results.get(0) : null;
    }
}
