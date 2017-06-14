package ua.nure.ipz.zoo.repository;

import ua.nure.ipz.zoo.entity.Product;

public interface ProductRepository extends Repository<Product> {
    Product findByName(String name);
}
