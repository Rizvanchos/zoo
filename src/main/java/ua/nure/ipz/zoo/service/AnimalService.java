package ua.nure.ipz.zoo.service;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;
import ua.nure.ipz.zoo.dto.AnimalDto;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public interface AnimalService extends Service<AnimalDto> {
    UUID create(@NotBlank String name, @NotBlank String type, @URL String imageUrl);

    void addDescription(@NotNull UUID animalId, @NotBlank String description);

    void changeName(@NotNull UUID animalId, @NotBlank String newName);

    void changeAviary(@NotNull UUID animalId, @NotNull UUID aviaryId);

    void uploadAnimalPhoto(@NotNull UUID animalId, @URL String imageUrl);

    void delete(@NotNull UUID animalId);
}
