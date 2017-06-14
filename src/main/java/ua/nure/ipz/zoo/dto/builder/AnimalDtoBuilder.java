package ua.nure.ipz.zoo.dto.builder;

import org.springframework.stereotype.Component;
import ua.nure.ipz.zoo.dto.AnimalDto;
import ua.nure.ipz.zoo.entity.Animal;
import ua.nure.ipz.zoo.entity.Aviary;

import java.util.Objects;
import java.util.UUID;

@Component
public class AnimalDtoBuilder implements DtoBuilder<Animal, AnimalDto> {
    @Override
    public AnimalDto toDto(Animal source) {
        return new AnimalDto(source.getDomainId(), getAviaryDomainId(source.getAviary()), source.getName(), source.getType(),
                source.getImageUrl(), source.getDescription());
    }

    private UUID getAviaryDomainId(Aviary aviary) {
        return Objects.nonNull(aviary) ? aviary.getDomainId() : null;
    }
}
