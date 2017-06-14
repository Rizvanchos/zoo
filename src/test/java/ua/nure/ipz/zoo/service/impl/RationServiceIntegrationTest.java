package ua.nure.ipz.zoo.service.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import ua.nure.ipz.zoo.dto.RationDto;
import ua.nure.ipz.zoo.exception.DuplicateNamedEntityException;
import ua.nure.ipz.zoo.exception.DuplicationEntityException;
import ua.nure.ipz.zoo.exception.RemovingUnexistingItemException;
import ua.nure.ipz.zoo.exception.ServiceValidationException;
import ua.nure.ipz.zoo.service.AnimalService;
import ua.nure.ipz.zoo.service.ProductService;
import ua.nure.ipz.zoo.service.RationService;

import java.math.BigDecimal;
import java.util.UUID;

import static org.hamcrest.Matchers.empty;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ua.nure.ipz.zoo.config.ZooJUnitConfiguration.class)
@Transactional
public class RationServiceIntegrationTest {

    private static final String NAME = "Name";
    private static final String TYPE = "Type";
    private static final String IMAGE_URL = "http://zoo.sandiegozoo.org";

    private static final BigDecimal PRICE = BigDecimal.ONE;
    private static final float NEW_QUANTITY = 10;
    private static final float QUANTITY = 5;
    private static final int FLOAT_DELTA = 0;
    private static final int INVALID_QUANTITY = 0;

    @Autowired
    private RationService rationService;
    @Autowired
    private AnimalService animalService;
    @Autowired
    private ProductService productService;

    private UUID rationId;
    private UUID animalId;
    private UUID productId;

    @Before
    public void setUp() throws Exception {
        animalId = animalService.create(NAME, TYPE, IMAGE_URL);
        rationId = rationService.create(animalId);
        productId = productService.create(NAME, PRICE);
    }

    @Test
    public void shouldCreateNewRation_whenValidAnimalId() {
        RationDto rationDto = rationService.view(rationId);
        assertEquals(animalId, rationDto.getAnimalId());
    }

    @Test(expected = ServiceValidationException.class)
    public void shouldThrowException_whenAddItemToNullRation() throws DuplicateNamedEntityException, DuplicationEntityException {
        rationService.addItem(null, productId, QUANTITY);
    }

    @Test(expected = ServiceValidationException.class)
    public void shouldThrowException_whenAddItemWithNullProduct() throws DuplicationEntityException {
        rationService.addItem(rationId, null, QUANTITY);
    }

    @Test(expected = ServiceValidationException.class)
    public void shouldThrowException_whenAddItemWithInvalidQuantity() throws DuplicateNamedEntityException, DuplicationEntityException {
        rationService.addItem(rationId, productId, INVALID_QUANTITY);
    }

    @Test
    public void shouldAddItemToRation_whenPassValidParameters() throws DuplicateNamedEntityException, DuplicationEntityException {
        rationService.addItem(rationId, productId, QUANTITY);

        RationDto rationDto = rationService.view(rationId);
        assertEquals(animalId, rationDto.getAnimalId());
        assertEquals(productId, rationDto.getFoods().get(0).getProductId());
        assertEquals(QUANTITY, rationDto.getFoods().get(0).getQuantity(), FLOAT_DELTA);
    }

    @Test(expected = ServiceValidationException.class)
    public void shouldThrowException_whenUpdateItemToNullRation() {
        rationService.updateItem(null, productId, QUANTITY);
    }

    @Test(expected = ServiceValidationException.class)
    public void shouldThrowException_whenUpdateItemWithNullProduct() {
        rationService.updateItem(rationId, null, QUANTITY);
    }

    @Test(expected = ServiceValidationException.class)
    public void shouldThrowException_whenUpdateItemWithInvalidQuantity() {
        rationService.updateItem(rationId, productId, INVALID_QUANTITY);
    }

    @Test
    public void shouldUpdateItemToRation_whenPassValidParameters() throws DuplicationEntityException {
        rationService.addItem(rationId, productId, QUANTITY);
        rationService.updateItem(rationId, productId, NEW_QUANTITY);

        RationDto rationDto = rationService.view(rationId);
        assertEquals(NEW_QUANTITY, rationDto.getFoods().get(0).getQuantity(), FLOAT_DELTA);
    }

    @Test(expected = ServiceValidationException.class)
    public void shouldThrowException_whenRemoveItemToNullRation() throws RemovingUnexistingItemException {
        rationService.removeItem(null, productId);
    }

    @Test(expected = ServiceValidationException.class)
    public void shouldThrowException_whenRemoveItemWithNullProduct() throws RemovingUnexistingItemException {
        rationService.removeItem(rationId, null);
    }

    @Test(expected = ServiceValidationException.class)
    public void shouldThrowException_whenRemovingUnexistingItem() throws RemovingUnexistingItemException {
        rationService.removeItem(rationId, UUID.randomUUID());
    }

    @Test
    public void shouldRemoveItemFromRation_whenCallRemoveItem() throws DuplicationEntityException, RemovingUnexistingItemException {
        rationService.addItem(rationId, productId, QUANTITY);
        rationService.removeItem(rationId, productId);

        RationDto rationDto = rationService.view(rationId);
        assertThat(rationDto.getFoods(), empty());
    }

    @Test
    public void shouldDeleteRation_whenCallDelete(){
        rationService.delete(rationId);
        assertThat(rationService.viewAll(), empty());
    }
}
