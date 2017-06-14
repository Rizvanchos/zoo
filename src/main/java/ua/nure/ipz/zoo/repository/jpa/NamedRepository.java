package ua.nure.ipz.zoo.repository.jpa;

import ua.nure.ipz.zoo.util.DomainEntity;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public abstract class NamedRepository<T extends DomainEntity> extends BasicRepository<T> {
    protected NamedRepository(Class<T> clazz) {
        super(clazz);
    }

    public T findByName(String name) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<T> criteria = cb.createQuery(getEntityClass());
        Root<T> root = criteria.from(getEntityClass());
        criteria.select(root).where(cb.equal(root.get("name"), name));

        return getSingleResult(getEntityManager().createQuery(criteria).getResultList());
    }

}
