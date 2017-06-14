package ua.nure.ipz.zoo.dto.builder;

import ua.nure.ipz.zoo.dto.DomainEntityDto;
import ua.nure.ipz.zoo.util.DomainEntity;

public interface DtoBuilder<S extends DomainEntity, T extends DomainEntityDto> {
    T toDto(S source);
}
