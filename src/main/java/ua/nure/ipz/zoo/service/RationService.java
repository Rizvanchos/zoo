package ua.nure.ipz.zoo.service;

import ua.nure.ipz.zoo.dto.RationDto;
import ua.nure.ipz.zoo.exception.DuplicationEntityException;
import ua.nure.ipz.zoo.exception.RemovingUnexistingItemException;
import ua.nure.ipz.zoo.service.validation.Quantity;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public interface RationService extends Service<RationDto> {
    UUID create(@NotNull UUID animalId);

    void addItem(@NotNull UUID rationId, @NotNull UUID productId, @Quantity float quantity) throws DuplicationEntityException;

    void updateItem(@NotNull UUID rationId, @NotNull UUID productId, @Quantity float quantity);

    void removeItem(@NotNull UUID rationId, @NotNull UUID productId) throws RemovingUnexistingItemException;

    void delete(@NotNull UUID rationId);
}
