package ua.nure.ipz.zoo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.nure.ipz.zoo.dto.AnimalDto;
import ua.nure.ipz.zoo.dto.AviaryDto;
import ua.nure.ipz.zoo.dto.builder.DtoBuilder;
import ua.nure.ipz.zoo.entity.Animal;
import ua.nure.ipz.zoo.entity.Aviary;
import ua.nure.ipz.zoo.entity.Schedule;
import ua.nure.ipz.zoo.exception.NotContactAviaryException;
import ua.nure.ipz.zoo.exception.DuplicationEntityException;
import ua.nure.ipz.zoo.repository.AnimalRepository;
import ua.nure.ipz.zoo.repository.AviaryRepository;
import ua.nure.ipz.zoo.service.AviaryService;
import ua.nure.ipz.zoo.service.ServiceUtils;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DefaultAviaryService implements AviaryService {
    @Autowired
    private AviaryRepository aviaryRepository;
    @Autowired
    private AnimalRepository animalRepository;
    @Autowired
    private DtoBuilder<Aviary, AviaryDto> aviaryDtoBuilder;
    @Autowired
    private DtoBuilder<Animal, AnimalDto> animalDtoBuilder;

    @Override
    @Transactional
    public UUID create(int number, float temperature, float wet, boolean contact) {
        Aviary aviary = new Aviary(number, temperature, wet, contact);
        aviaryRepository.save(aviary);
        return aviary.getDomainId();
    }

    @Override
    @Transactional
    public void addAnimal(UUID aviaryId, UUID animalId) throws DuplicationEntityException {
        Aviary aviary = resolveAviary(aviaryId);
        Animal animal = resolveAnimal(animalId);

        if (aviary.contains(animal)) {
            throw new DuplicationEntityException(aviary.getAnimals(), animal.getName());
        }

        animal.setAviary(aviary);
        aviary.getAnimals().add(animal);
    }

    @Override
    @Transactional
    public void addVisitTime(UUID aviaryId, String visitTime) throws NotContactAviaryException, DuplicationEntityException {
        Aviary aviary = resolveAviary(aviaryId);
        Schedule schedule = aviary.getSchedule();

        if (!aviary.isContact()) {
            throw new NotContactAviaryException(aviaryId);
        }
        if (schedule.getVisitTimes().contains(visitTime)) {
            throw new DuplicationEntityException(schedule.getVisitTimes(), visitTime);
        }

        schedule.addTime(visitTime);
    }

    @Override
    public List<AnimalDto> viewAnimals(UUID aviaryId) {
        Aviary aviary = resolveAviary(aviaryId);
        return aviary.getAnimals().stream().map(animalDtoBuilder::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void changeTemperature(UUID aviaryId, float newTemperature) {
        Aviary aviary = resolveAviary(aviaryId);
        aviary.setTemperature(newTemperature);
    }

    @Override
    @Transactional
    public void changeWet(UUID aviaryId, float newWet) {
        Aviary aviary = resolveAviary(aviaryId);
        aviary.setWet(newWet);
    }

    @Override
    @Transactional
    public void changeContact(UUID aviaryId, boolean isContact) {
        Aviary aviary = resolveAviary(aviaryId);
        aviary.setContact(isContact);
    }

    @Override
    public String viewSchedule(UUID aviaryId) throws NotContactAviaryException {
        Aviary aviary = resolveAviary(aviaryId);

        if (!aviary.isContact()) {
            throw new NotContactAviaryException(aviaryId);
        }

        return aviary.viewSchedule();
    }

    @Override
    @Transactional
    public void delete(UUID aviaryId) {
        Aviary aviary = resolveAviary(aviaryId);
        aviaryRepository.delete(aviary);
    }

    @Override
    public List<UUID> viewAll() {
        return aviaryRepository.selectAllDomainIds();
    }

    @Override
    public AviaryDto view(UUID domainId) {
        Aviary aviary = resolveAviary(domainId);
        return aviaryDtoBuilder.toDto(aviary);
    }

    private Aviary resolveAviary(UUID aviaryId) {
        return ServiceUtils.resolveEntity(aviaryRepository, aviaryId);
    }

    private Animal resolveAnimal(UUID animalId) {
        return ServiceUtils.resolveEntity(animalRepository, animalId);
    }
}
