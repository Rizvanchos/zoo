package ua.nure.ipz.zoo.service;

import org.springframework.validation.annotation.Validated;
import ua.nure.ipz.zoo.dto.DomainEntityDto;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Validated
public interface Service<T extends DomainEntityDto> {
    List<UUID> viewAll();

    T view(@NotNull UUID domainId);
}