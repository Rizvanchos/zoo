package ua.nure.ipz.zoo.entity;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Test;

public class ProductTest {

    private static final String PRODUCT_NAME = "Product";
    private static final BigDecimal PRODUCT_PRICE = BigDecimal.TEN;

    @Test
    public void shouldCreateProductWithParameters_whenConstructorWithParameters() {
        Product product = new Product(PRODUCT_NAME, PRODUCT_PRICE);
        assertEquals(PRODUCT_NAME, product.getName());
        assertEquals(PRODUCT_PRICE, product.getPrice());
    }

    @Test
    public void shouldSetNewProductName_whenSetNewName(){
        Product product = new Product();
        product.setName(PRODUCT_NAME);

        assertEquals(PRODUCT_NAME, product.getName());
    }

    @Test
    public void shouldSetNewProductPrice_whenSetNewPrice(){
        Product product = new Product();
        product.setPrice(PRODUCT_PRICE);

        assertEquals(PRODUCT_PRICE, product.getPrice());
    }
}
