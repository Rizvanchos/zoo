package ua.nure.ipz.zoo.dto;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class RationDto extends DomainEntityDto<RationDto> {
    private List<FoodDto> foods;
    private UUID animalId;

    public RationDto(UUID domainId, List<FoodDto> foods, UUID animalId) {
        super(domainId);
        this.foods = foods;
        this.animalId = animalId;
    }

    public List<FoodDto> getFoods() {
        return foods;
    }

    public UUID getAnimalId() {
        return animalId;
    }

    @Override
    protected List<Object> getAttributesToIncludeInEqualityCheck() {
        return Arrays.asList(getDomainId(), getAnimalId(), getFoods());
    }

    @Override
    public String toString() {
        String foodNames = foods.stream()
                .map(food -> "\tProductId: " + food.getDomainId() + ", quantity: " + food.getQuantity() + "\n")
                .reduce(String::concat).get().trim();
        return String.format("ID = %s\nFoods: \n\t%s\nAnimalId = %s", getDomainId(), foodNames, getAnimalId());
    }
}
