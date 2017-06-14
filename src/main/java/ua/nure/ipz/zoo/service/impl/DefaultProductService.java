package ua.nure.ipz.zoo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.nure.ipz.zoo.dto.ProductDto;
import ua.nure.ipz.zoo.dto.builder.DtoBuilder;
import ua.nure.ipz.zoo.entity.Product;
import ua.nure.ipz.zoo.exception.DuplicateNamedEntityException;
import ua.nure.ipz.zoo.repository.ProductRepository;
import ua.nure.ipz.zoo.service.ProductService;
import ua.nure.ipz.zoo.service.ServiceUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class DefaultProductService implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private DtoBuilder<Product, ProductDto> productDtoBuilder;

    @Override
    @Transactional
    public UUID create(String name, BigDecimal price) throws DuplicateNamedEntityException {
        checkDuplicateName(name);

        Product product = new Product(name, price);
        productRepository.save(product);
        return product.getDomainId();
    }

    @Override
    @Transactional
    public void changeName(UUID productId, String newName) throws DuplicateNamedEntityException {
        checkDuplicateName(newName);

        Product product = resolveProduct(productId);
        product.setName(newName);
    }

    @Override
    @Transactional
    public void changePrice(UUID productId, BigDecimal price) {
        Product product = resolveProduct(productId);
        product.setPrice(price);
    }

    @Override
    @Transactional
    public void delete(UUID productId) {
        Product product = resolveProduct(productId);
        productRepository.delete(product);
    }

    @Override
    public List<UUID> viewAll() {
        return productRepository.selectAllDomainIds();
    }

    @Override
    public ProductDto view(UUID domainId) {
        Product product = resolveProduct(domainId);
        return productDtoBuilder.toDto(product);
    }

    private void checkDuplicateName(String name) throws DuplicateNamedEntityException {
        if (productRepository.findByName(name) != null) {
            throw new DuplicateNamedEntityException(Product.class, name);
        }
    }

    private Product resolveProduct(UUID productId) {
        return ServiceUtils.resolveEntity(productRepository, productId);
    }
}
