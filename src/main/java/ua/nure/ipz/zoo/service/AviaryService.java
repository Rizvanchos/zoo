package ua.nure.ipz.zoo.service;

import org.hibernate.validator.constraints.NotBlank;
import ua.nure.ipz.zoo.dto.AnimalDto;
import ua.nure.ipz.zoo.dto.AviaryDto;
import ua.nure.ipz.zoo.exception.DuplicationEntityException;
import ua.nure.ipz.zoo.exception.NotContactAviaryException;
import ua.nure.ipz.zoo.service.validation.Temperature;
import ua.nure.ipz.zoo.service.validation.Wet;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

public interface AviaryService extends Service<AviaryDto> {
    UUID create(@Min(0) int number, @Temperature float temperature, @Wet float wet, boolean contact);

    void addAnimal(@NotNull UUID aviaryId, @NotNull UUID animalId) throws DuplicationEntityException;

    void addVisitTime(@NotNull UUID aviaryId, @NotBlank String time) throws NotContactAviaryException, DuplicationEntityException;

    List<AnimalDto> viewAnimals(@NotNull UUID aviaryId);

    void changeTemperature(@NotNull UUID aviaryId, @Temperature float newTemperature);

    void changeWet(@NotNull UUID aviaryId, @Wet float newWet);

    void changeContact(@NotNull UUID aviaryId, boolean isContact);

    String viewSchedule(@NotNull UUID aviaryId) throws NotContactAviaryException;

    void delete(@NotNull UUID aviaryId);
}
