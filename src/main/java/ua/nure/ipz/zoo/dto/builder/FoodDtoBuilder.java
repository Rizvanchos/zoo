package ua.nure.ipz.zoo.dto.builder;

import org.springframework.stereotype.Component;
import ua.nure.ipz.zoo.dto.FoodDto;
import ua.nure.ipz.zoo.entity.Food;

@Component
public class FoodDtoBuilder implements DtoBuilder<Food, FoodDto> {
    @Override
    public FoodDto toDto(Food source) {
        return new FoodDto(source.getDomainId(), source.getProduct().getDomainId(), source.getQuantity());
    }
}
