package ua.nure.ipz.zoo.repository.jpa;

import org.springframework.stereotype.Repository;
import ua.nure.ipz.zoo.entity.Animal;
import ua.nure.ipz.zoo.repository.AnimalRepository;

@Repository
public class DefaultAnimalRepository extends BasicRepository<Animal> implements AnimalRepository {
    public DefaultAnimalRepository() {
        super(Animal.class);
    }
}
