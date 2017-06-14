package ua.nure.ipz.zoo.repository.jpa;

import org.springframework.stereotype.Repository;
import ua.nure.ipz.zoo.entity.Ration;
import ua.nure.ipz.zoo.repository.RationRepository;

@Repository
public class DefaultRationRepository extends BasicRepository<Ration> implements RationRepository {
    public DefaultRationRepository() {
        super(Ration.class);
    }
}
