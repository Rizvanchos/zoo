package ua.nure.ipz.zoo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.nure.ipz.zoo.dto.RationDto;
import ua.nure.ipz.zoo.dto.builder.DtoBuilder;
import ua.nure.ipz.zoo.entity.Animal;
import ua.nure.ipz.zoo.entity.Food;
import ua.nure.ipz.zoo.entity.Product;
import ua.nure.ipz.zoo.entity.Ration;
import ua.nure.ipz.zoo.exception.RemovingUnexistingItemException;
import ua.nure.ipz.zoo.exception.DuplicationEntityException;
import ua.nure.ipz.zoo.repository.AnimalRepository;
import ua.nure.ipz.zoo.repository.ProductRepository;
import ua.nure.ipz.zoo.repository.RationRepository;
import ua.nure.ipz.zoo.service.RationService;
import ua.nure.ipz.zoo.service.ServiceUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DefaultRationService implements RationService {
    @Autowired
    private RationRepository rationRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private AnimalRepository animalRepository;
    @Autowired
    private DtoBuilder<Ration, RationDto> rationDtoBuilder;

    @Override
    @Transactional
    public UUID create(UUID animalId) {
        Animal animal = resolveAnimal(animalId);
        Ration ration = new Ration(animal);

        rationRepository.save(ration);
        return ration.getDomainId();
    }

    @Override
    @Transactional
    public void addItem(UUID rationId, UUID productId, float quantity) throws DuplicationEntityException {
        Ration ration = resolveRation(rationId);
        Product product = resolveProduct(productId);

        Food food = new Food(product, quantity);
        List<Food> foods = ration.getFoods();

        if (foods.contains(food)) {
            throw new DuplicationEntityException(foods, food.toString());
        }

        foods.add(food);
    }

    @Override
    @Transactional
    public void updateItem(UUID rationId, UUID productId, float quantity) {
        Ration ration = resolveRation(rationId);
        Product product = resolveProduct(productId);

        Optional<Food> oldFood = findFoodByProduct(ration, product);
        if (oldFood.isPresent()) {
            oldFood.get().setQuantity(quantity);
        }
    }

    private Optional<Food> findFoodByProduct(Ration ration, Product product) {
        return ration.getFoods().stream().filter(food -> food.getProduct().equals(product)).findFirst();
    }

    @Override
    @Transactional
    public void removeItem(UUID rationId, UUID productId) throws RemovingUnexistingItemException {
        Ration ration = resolveRation(rationId);
        Product product = resolveProduct(productId);

        Optional<Food> removingFood = findFoodByProduct(ration, product);
        if (!removingFood.isPresent()) {
            throw new RemovingUnexistingItemException(product.getName(), "food", productId);
        }

        ration.getFoods().remove(removingFood.get());
    }

    @Override
    @Transactional
    public void delete(UUID rationId) {
        Ration ration = resolveRation(rationId);
        rationRepository.delete(ration);
    }

    @Override
    public List<UUID> viewAll() {
        return rationRepository.selectAllDomainIds();
    }

    @Override
    public RationDto view(UUID domainId) {
        Ration ration = resolveRation(domainId);
        return rationDtoBuilder.toDto(ration);
    }

    private Ration resolveRation(UUID rationId) {
        return ServiceUtils.resolveEntity(rationRepository, rationId);
    }

    private Product resolveProduct(UUID productId) {
        return ServiceUtils.resolveEntity(productRepository, productId);
    }

    private Animal resolveAnimal(UUID animalId) {
        return ServiceUtils.resolveEntity(animalRepository, animalId);
    }
}
