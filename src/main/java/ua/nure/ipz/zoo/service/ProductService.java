package ua.nure.ipz.zoo.service;

import org.hibernate.validator.constraints.NotBlank;
import ua.nure.ipz.zoo.dto.ProductDto;
import ua.nure.ipz.zoo.exception.DuplicateNamedEntityException;
import ua.nure.ipz.zoo.service.validation.Price;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

public interface ProductService extends Service<ProductDto> {
    UUID create(@NotBlank String name, @Price BigDecimal price) throws DuplicateNamedEntityException;

    void changeName(@NotNull UUID productId, @NotBlank String newName) throws DuplicateNamedEntityException;

    void changePrice(@NotNull UUID productId, @Price BigDecimal price);

    void delete(@NotNull UUID productId);
}
