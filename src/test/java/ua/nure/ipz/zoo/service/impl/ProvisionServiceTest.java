package ua.nure.ipz.zoo.service.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import ua.nure.ipz.zoo.dto.FoodDto;
import ua.nure.ipz.zoo.dto.ProvisionDto;
import ua.nure.ipz.zoo.exception.DuplicateNamedEntityException;
import ua.nure.ipz.zoo.exception.DuplicationEntityException;
import ua.nure.ipz.zoo.exception.RemovingUnexistingItemException;
import ua.nure.ipz.zoo.exception.ServiceValidationException;
import ua.nure.ipz.zoo.service.AnimalService;
import ua.nure.ipz.zoo.service.ProductService;
import ua.nure.ipz.zoo.service.ProvisionService;
import ua.nure.ipz.zoo.service.RationService;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.empty;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ua.nure.ipz.zoo.config.ZooJUnitConfiguration.class)
@Transactional
public class ProvisionServiceTest {

    private static final String NAME = "Name";
    private static final String TYPE = "Type";
    private static final String IMAGE_URL = "http://zoo.sandiegozoo.org";
    private static final BigDecimal PRICE = BigDecimal.ONE;
    private static final int FLOAT_DELTA = 0;
    private static final float QUANTITY = 1;

    @Autowired
    private ProvisionService provisionService;
    @Autowired
    private ProductService productService;
    @Autowired
    private RationService rationService;
    @Autowired
    private AnimalService animalService;

    private UUID provisionId;
    private UUID rationId;
    private UUID newRationId;

    @Before
    public void setUp() {
        UUID animalId = animalService.create(NAME, TYPE, IMAGE_URL);
        rationId = rationService.create(animalId);
        newRationId = rationService.create(animalId);
        provisionId = provisionService.create();
    }

    @Test
    public void shouldCreateNewProvision_whenCallCreate() {
        assertNotNull(provisionId);
    }

    @Test(expected = ServiceValidationException.class)
    public void shouldThrowException_whenAddItemToNullProvision() throws DuplicationEntityException {
        provisionService.addRation(null, rationId);
    }

    @Test(expected = ServiceValidationException.class)
    public void shouldThrowException_whenAddItemWithNullRation() throws DuplicationEntityException {
        provisionService.addRation(provisionId, null);
    }

    @Test
    public void shouldAddRationToProvision_whenPassValidParameters() throws DuplicationEntityException {
        provisionService.addRation(provisionId, rationId);

        ProvisionDto provisionDto = provisionService.view(provisionId);
        assertEquals(rationId, provisionDto.getRations().get(0).getDomainId());
    }

    @Test(expected = ServiceValidationException.class)
    public void shouldThrowException_whenUpdateRationWithNullProvision() {
        provisionService.updateRation(null, rationId, newRationId);
    }

    @Test(expected = ServiceValidationException.class)
    public void shouldThrowException_whenUpdateRationWithNullOldRation() {
        provisionService.updateRation(provisionId, null, newRationId);
    }

    @Test(expected = ServiceValidationException.class)
    public void shouldThrowException_whenUpdateRationWithNullNewRation() {
        provisionService.updateRation(provisionId, rationId, null);
    }

    @Test
    public void shouldUpdateItemToRation_whenPassValidParameters() throws DuplicationEntityException {
        provisionService.addRation(provisionId, rationId);
        provisionService.updateRation(provisionId, rationId, newRationId);

        ProvisionDto provisionDto = provisionService.view(provisionId);
        assertEquals(newRationId, provisionDto.getRations().get(0).getDomainId());
    }

    @Test(expected = ServiceValidationException.class)
    public void shouldThrowException_whenRemoveRationWithNullProvision() throws RemovingUnexistingItemException {
        provisionService.removeRation(null, rationId);
    }

    @Test(expected = ServiceValidationException.class)
    public void shouldThrowException_whenRemoveRationWithNullRation() throws RemovingUnexistingItemException {
        provisionService.removeRation(provisionId, null);
    }

    @Test(expected = ServiceValidationException.class)
    public void shouldThrowException_whenRemovingUnexistingRation() throws RemovingUnexistingItemException {
        provisionService.removeRation(provisionId, UUID.randomUUID());
    }

    @Test
    public void shouldRemoveRationFromProvision_whenCallRemoveItem() throws DuplicationEntityException, RemovingUnexistingItemException {
        provisionService.addRation(provisionId, rationId);
        provisionService.removeRation(provisionId, rationId);

        ProvisionDto provisionDto = provisionService.view(provisionId);
        assertThat(provisionDto.getRations(), empty());
    }

    @Test
    public void shouldDeleteRation_whenCallDelete() {
        provisionService.delete(provisionId);
        assertThat(provisionService.viewAll(), empty());
    }

    @Test
    public void shouldReturnNeedsListWithProductsInRation_whenViewNeedsList() throws DuplicationEntityException, DuplicateNamedEntityException {
        UUID productId = productService.create(NAME, PRICE);

        rationService.addItem(rationId,productId, QUANTITY);
        provisionService.addRation(provisionId, rationId);

        List<FoodDto> needsList = provisionService.viewNeedsList(provisionId);
        assertEquals(productId, needsList.get(0).getProductId());
        assertEquals(QUANTITY, needsList.get(0).getQuantity(), FLOAT_DELTA);
    }
}
