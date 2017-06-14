package ua.nure.ipz.zoo.repository.jpa;

import org.springframework.stereotype.Repository;
import ua.nure.ipz.zoo.entity.Provision;
import ua.nure.ipz.zoo.repository.ProvisionRepository;

@Repository
public class DefaultProvisionRepository extends BasicRepository<Provision> implements ProvisionRepository {
    public DefaultProvisionRepository() {
        super(Provision.class);
    }
}
