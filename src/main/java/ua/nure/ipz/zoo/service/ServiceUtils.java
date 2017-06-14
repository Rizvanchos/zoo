package ua.nure.ipz.zoo.service;

import ua.nure.ipz.zoo.exception.ServiceUnresolvedEntityException;
import ua.nure.ipz.zoo.repository.Repository;
import ua.nure.ipz.zoo.util.DomainEntity;

import java.util.UUID;

public final class ServiceUtils {
    public static <T extends DomainEntity> T resolveEntity(Repository<T> repository, UUID domainId) {
        T entity = repository.findByDomainId(domainId);

        if (entity != null) {
            return entity;
        }

        throw new ServiceUnresolvedEntityException(repository.getEntityClass(), domainId);
    }
}
