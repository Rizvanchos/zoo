package ua.nure.ipz.zoo.repository.jpa;

import org.springframework.stereotype.Repository;
import ua.nure.ipz.zoo.entity.Product;
import ua.nure.ipz.zoo.repository.ProductRepository;

@Repository
public class DefaultProductRepository extends NamedRepository<Product> implements ProductRepository {
    public DefaultProductRepository() {
        super(Product.class);
    }
}
