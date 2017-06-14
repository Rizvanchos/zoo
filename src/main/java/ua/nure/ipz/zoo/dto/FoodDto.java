package ua.nure.ipz.zoo.dto;

import ua.nure.ipz.zoo.entity.Food;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class FoodDto extends DomainEntityDto<Food> {
    private UUID productId;
    private float quantity;

    public FoodDto(UUID domainId, UUID productId, float quantity) {
        super(domainId);
        this.productId = productId;
        this.quantity = quantity;
    }

    public UUID getProductId() {
        return productId;
    }

    public float getQuantity() {
        return quantity;
    }

    @Override
    protected List<Object> getAttributesToIncludeInEqualityCheck() {
        return Arrays.asList(getDomainId(), getProductId(), getQuantity());
    }

    @Override
    public String toString() {
        return String.format("ID = %s\nProductId= %s\nQuantity = %s", getDomainId(), getProductId(), getQuantity());
    }
}
