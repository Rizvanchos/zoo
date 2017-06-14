package ua.nure.ipz.zoo.dto.builder;

import org.springframework.stereotype.Component;
import ua.nure.ipz.zoo.dto.ProductDto;
import ua.nure.ipz.zoo.entity.Product;

@Component
public class ProductDtoBuilder implements DtoBuilder<Product, ProductDto> {
    @Override
    public ProductDto toDto(Product source) {
        return new ProductDto(source.getDomainId(), source.getName(), source.getPrice());
    }
}
