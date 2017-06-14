package ua.nure.ipz.zoo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.nure.ipz.zoo.dto.AnimalDto;
import ua.nure.ipz.zoo.dto.builder.DtoBuilder;
import ua.nure.ipz.zoo.entity.Animal;
import ua.nure.ipz.zoo.entity.Aviary;
import ua.nure.ipz.zoo.repository.AnimalRepository;
import ua.nure.ipz.zoo.repository.AviaryRepository;
import ua.nure.ipz.zoo.service.AnimalService;
import ua.nure.ipz.zoo.service.ServiceUtils;

import java.util.List;
import java.util.UUID;

@Service
public class DefaultAnimalService implements AnimalService {
    @Autowired
    private AnimalRepository animalRepository;
    @Autowired
    private AviaryRepository aviaryRepository;
    @Autowired
    private DtoBuilder<Animal, AnimalDto> animalDtoBuilder;

    @Override
    @Transactional
    public UUID create(String name, String type, String imageUrl) {
        Animal animal = new Animal(name, type, imageUrl);
        animalRepository.save(animal);
        return animal.getDomainId();
    }

    @Override
    @Transactional
    public void addDescription(UUID animalId, String description) {
        Animal animal = resolveAnimal(animalId);
        animal.setDescription(description);
    }

    @Override
    @Transactional
    public void changeName(UUID animalId, String newName) {
        Animal animal = resolveAnimal(animalId);
        animal.setName(newName);
    }

    @Override
    @Transactional
    public void changeAviary(UUID animalId, UUID aviaryId) {
        Animal animal = resolveAnimal(animalId);
        Aviary aviary = resolveAviary(aviaryId);

        aviary.getAnimals().add(animal);
    }

    @Override
    @Transactional
    public void uploadAnimalPhoto(UUID animalId, String imageUrl) {
        Animal animal = resolveAnimal(animalId);
        animal.setImageUrl(imageUrl);
    }

    @Override
    @Transactional
    public void delete(UUID animalId) {
        Animal animal = resolveAnimal(animalId);
        animalRepository.delete(animal);
    }

    @Override
    public List<UUID> viewAll() {
        return animalRepository.selectAllDomainIds();
    }

    @Override
    public AnimalDto view(UUID domainId) {
        Animal animal = resolveAnimal(domainId);
        return animalDtoBuilder.toDto(animal);
    }

    private Animal resolveAnimal(UUID animalId) {
        return ServiceUtils.resolveEntity(animalRepository, animalId);
    }

    private Aviary resolveAviary(UUID aviaryId) {
        return ServiceUtils.resolveEntity(aviaryRepository, aviaryId);
    }
}
