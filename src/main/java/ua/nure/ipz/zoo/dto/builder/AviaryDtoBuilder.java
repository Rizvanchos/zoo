package ua.nure.ipz.zoo.dto.builder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.nure.ipz.zoo.dto.AnimalDto;
import ua.nure.ipz.zoo.dto.AviaryDto;
import ua.nure.ipz.zoo.entity.Animal;
import ua.nure.ipz.zoo.entity.Aviary;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AviaryDtoBuilder implements DtoBuilder<Aviary, AviaryDto> {
    @Autowired
    private DtoBuilder<Animal, AnimalDto> animalDtoBuilder;

    @Override
    public AviaryDto toDto(Aviary source) {
        return new AviaryDto(source.getDomainId(), getAnimalsDto(source.getAnimals()), source.getNumber(), source.getSchedule().getVisitTimes(),
                source.isContact(), source.getTemperature(), source.getWet());
    }

    private List<AnimalDto> getAnimalsDto(List<Animal> animals) {
        return animals.stream().map(animalDtoBuilder::toDto).collect(Collectors.toList());
    }
}
