package ua.nure.ipz.zoo.dto.builder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.nure.ipz.zoo.dto.FoodDto;
import ua.nure.ipz.zoo.dto.RationDto;
import ua.nure.ipz.zoo.entity.Food;
import ua.nure.ipz.zoo.entity.Ration;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RationDtoBuilder implements DtoBuilder<Ration, RationDto> {
    @Autowired
    private DtoBuilder<Food, FoodDto> foodDtoBuilder;

    @Override
    public RationDto toDto(Ration source) {
        return new RationDto(source.getDomainId(), getFoodDto(source.getFoods()), source.getAnimal().getDomainId());
    }

    private List<FoodDto> getFoodDto(List<Food> products) {
        return products.stream().map(foodDtoBuilder::toDto).collect(Collectors.toList());
    }
}
