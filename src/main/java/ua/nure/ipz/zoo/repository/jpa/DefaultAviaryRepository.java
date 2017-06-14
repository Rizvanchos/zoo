package ua.nure.ipz.zoo.repository.jpa;

import org.springframework.stereotype.Repository;
import ua.nure.ipz.zoo.entity.Aviary;
import ua.nure.ipz.zoo.repository.AviaryRepository;

@Repository
public class DefaultAviaryRepository extends BasicRepository<Aviary> implements AviaryRepository {
    public DefaultAviaryRepository() {
        super(Aviary.class);
    }
}
