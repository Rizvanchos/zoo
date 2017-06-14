package ua.nure.ipz.zoo.service.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import ua.nure.ipz.zoo.dto.ProductDto;
import ua.nure.ipz.zoo.exception.DuplicateNamedEntityException;
import ua.nure.ipz.zoo.exception.ServiceValidationException;
import ua.nure.ipz.zoo.service.ProductService;

import java.math.BigDecimal;
import java.util.UUID;

import static org.hamcrest.Matchers.empty;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ua.nure.ipz.zoo.config.ZooJUnitConfiguration.class)
@Transactional
public class ProductServiceTest {

    private static final String NAME = "Name";
    private static final String NEW_NAME = "newName";
    private static final BigDecimal PRICE = BigDecimal.ONE;
    private static final BigDecimal NEW_PRICE = BigDecimal.TEN;
    private static final BigDecimal INVALID_PRICE = BigDecimal.ZERO;
    private static final String EMPTY_STRING = "";

    @Autowired
    private ProductService productService;

    private UUID productId;

    @Before
    public void setUp() throws DuplicateNamedEntityException {
        productId = productService.create(NAME, PRICE);
    }

    @Test
    public void shouldCreateProduct_whenPassValidParameters() throws DuplicateNamedEntityException {
        ProductDto productDto = productService.view(productId);

        assertEquals(NAME, productDto.getName());
        assertEquals(PRICE, productDto.getPrice());
    }

    @Test(expected = ServiceValidationException.class)
    public void shouldThrowException_whenCreateProductWithEmptyName() throws DuplicateNamedEntityException {
        productService.create(EMPTY_STRING, PRICE);
    }

    @Test(expected = ServiceValidationException.class)
    public void shouldThrowException_whenCreateProductWithInvalidPrice() throws DuplicateNamedEntityException {
        productService.create(NAME, INVALID_PRICE);
    }

    @Test(expected = ServiceValidationException.class)
    public void shouldThrowException_whenChangeProductNameOnEmpty() throws DuplicateNamedEntityException {
        productService.changeName(productId, "");
    }

    @Test
    public void shouldChangeProductName_whenCallChangeName() throws DuplicateNamedEntityException {
        productService.changeName(productId, NEW_NAME);

        ProductDto productDto = productService.view(productId);
        assertEquals(NEW_NAME, productDto.getName());
    }

    @Test(expected = ServiceValidationException.class)
    public void shouldThrowException_whenChangeProductPriceOnInvalid() {
        productService.changePrice(productId, INVALID_PRICE);
    }

    @Test
    public void shouldChangeProductPrice_whenCallChangePrice() {
        productService.changePrice(productId, NEW_PRICE);

        ProductDto productDto = productService.view(productId);
        assertEquals(NEW_PRICE, productDto.getPrice());
    }

    @Test
    public void shouldDeleteRation_whenCallDelete() {
        productService.delete(productId);
        assertThat(productService.viewAll(), empty());
    }
}
