package ua.nure.ipz.zoo.repository.jpa;

import org.springframework.stereotype.Repository;
import ua.nure.ipz.zoo.entity.Cart;
import ua.nure.ipz.zoo.repository.CartRepository;

@Repository
public class DefaultCartRepository extends BasicRepository<Cart> implements CartRepository {
    public DefaultCartRepository() {
        super(Cart.class);
    }
}
