package ua.nure.ipz.zoo.repository;

import ua.nure.ipz.zoo.util.DomainEntity;

import java.util.List;
import java.util.UUID;

public interface Repository<T extends DomainEntity> {
    Class<T> getEntityClass();

    T load(int id);

    Iterable<T> loadAll();

    void save(T t);

    void delete(T t);

    T findByDomainId(UUID domainId);

    List<UUID> selectAllDomainIds();
}
