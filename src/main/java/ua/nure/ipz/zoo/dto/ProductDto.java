package ua.nure.ipz.zoo.dto;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class ProductDto extends DomainEntityDto<ProductDto> {
    private String name;
    private BigDecimal price;

    public ProductDto(UUID domainId, String name, BigDecimal price) {
        super(domainId);
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    @Override
    protected List<Object> getAttributesToIncludeInEqualityCheck() {
        return Arrays.asList(getDomainId(), getName(), getPrice());
    }

    @Override
    public String toString() {
        return String.format("ID = %s\nName = %s\nPrice = %s", getDomainId(), getName(), getPrice());
    }
}
