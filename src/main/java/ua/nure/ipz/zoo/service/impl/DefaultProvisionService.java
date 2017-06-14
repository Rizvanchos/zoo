package ua.nure.ipz.zoo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.nure.ipz.zoo.dto.FoodDto;
import ua.nure.ipz.zoo.dto.ProvisionDto;
import ua.nure.ipz.zoo.dto.RationDto;
import ua.nure.ipz.zoo.dto.builder.DtoBuilder;
import ua.nure.ipz.zoo.entity.Food;
import ua.nure.ipz.zoo.entity.Provision;
import ua.nure.ipz.zoo.entity.Ration;
import ua.nure.ipz.zoo.exception.RemovingUnexistingItemException;
import ua.nure.ipz.zoo.exception.DuplicationEntityException;
import ua.nure.ipz.zoo.repository.ProvisionRepository;
import ua.nure.ipz.zoo.repository.RationRepository;
import ua.nure.ipz.zoo.service.ProvisionService;
import ua.nure.ipz.zoo.service.ServiceUtils;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DefaultProvisionService implements ProvisionService {
    @Autowired
    private ProvisionRepository provisionRepository;
    @Autowired
    private RationRepository rationRepository;
    @Autowired
    private DtoBuilder<Provision, ProvisionDto> provisionDtoBuilder;
    @Autowired
    private DtoBuilder<Food, FoodDto> foodDtoBuilder;

    @Override
    @Transactional
    public UUID create() {
        Provision provision = new Provision();
        provisionRepository.save(provision);
        return provision.getDomainId();
    }

    @Override
    public List<RationDto> viewRations(UUID provisionId) {
        return view(provisionId).getRations();
    }

    @Override
    public List<FoodDto> viewNeedsList(UUID provisionId) {
        Provision provision = resolveProvision(provisionId);
        return provision.getNeedsList().stream().map(foodDtoBuilder::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void addRation(UUID provisionId, UUID rationId) throws DuplicationEntityException {
        Provision provision = resolveProvision(provisionId);
        Ration ration = resolveRation(rationId);

        if (provision.contains(ration)) {
            throw new DuplicationEntityException(provision.getRations(), ration.toString());
        }

        provision.getRations().add(ration);
    }

    @Override
    @Transactional
    public void updateRation(UUID provisionId, UUID oldRationId, UUID newRationId) {
        Provision provision = resolveProvision(provisionId);
        Ration oldRation = resolveRation(oldRationId);
        Ration newRation = resolveRation(newRationId);

        List<Ration> rations = provision.getRations();
        rations.set(rations.indexOf(oldRation), newRation);
    }

    @Override
    @Transactional
    public void removeRation(UUID provisionId, UUID rationId) throws RemovingUnexistingItemException {
        Provision provision = resolveProvision(provisionId);
        Ration ration = resolveRation(rationId);

        List<Ration> rations = provision.getRations();
        if (!rations.contains(ration)) {
            throw new RemovingUnexistingItemException(ration.toString(), "ration", rationId);
        }

        rations.remove(ration);
    }

    @Override
    @Transactional
    public void delete(UUID provisionId) {
        Provision provision = resolveProvision(provisionId);
        provisionRepository.delete(provision);
    }

    @Override
    public List<UUID> viewAll() {
        return provisionRepository.selectAllDomainIds();
    }

    @Override
    public ProvisionDto view(UUID domainId) {
        Provision provision = resolveProvision(domainId);
        return provisionDtoBuilder.toDto(provision);
    }

    private Provision resolveProvision(UUID provisionId) {
        return ServiceUtils.resolveEntity(provisionRepository, provisionId);
    }

    private Ration resolveRation(UUID rationId) {
        return ServiceUtils.resolveEntity(rationRepository, rationId);
    }
}
