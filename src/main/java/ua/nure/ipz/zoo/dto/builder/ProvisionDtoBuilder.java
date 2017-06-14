package ua.nure.ipz.zoo.dto.builder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.nure.ipz.zoo.dto.ProvisionDto;
import ua.nure.ipz.zoo.dto.RationDto;
import ua.nure.ipz.zoo.entity.Provision;
import ua.nure.ipz.zoo.entity.Ration;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProvisionDtoBuilder implements DtoBuilder<Provision, ProvisionDto> {
    @Autowired
    private DtoBuilder<Ration, RationDto> rationDtoBuilder;

    @Override
    public ProvisionDto toDto(Provision source) {
        return new ProvisionDto(source.getDomainId(), getRationsDto(source.getRations()));
    }

    private List<RationDto> getRationsDto(List<Ration> rations) {
        return rations.stream().map(rationDtoBuilder::toDto).collect(Collectors.toList());
    }
}
