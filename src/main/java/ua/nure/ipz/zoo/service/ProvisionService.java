package ua.nure.ipz.zoo.service;

import ua.nure.ipz.zoo.dto.FoodDto;
import ua.nure.ipz.zoo.dto.ProvisionDto;
import ua.nure.ipz.zoo.dto.RationDto;
import ua.nure.ipz.zoo.exception.DuplicationEntityException;
import ua.nure.ipz.zoo.exception.RemovingUnexistingItemException;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

public interface ProvisionService extends Service<ProvisionDto> {
    UUID create();

    List<RationDto> viewRations(@NotNull UUID provisionId);

    List<FoodDto> viewNeedsList(@NotNull UUID provisionId);

    void addRation(@NotNull UUID provisionId, @NotNull UUID rationId) throws DuplicationEntityException;

    void updateRation(@NotNull UUID provisionId, @NotNull UUID oldRationId, @NotNull UUID newRationId);

    void removeRation(@NotNull UUID provisionId, @NotNull UUID rationId) throws RemovingUnexistingItemException;

    void delete(@NotNull UUID provisionId);
}
