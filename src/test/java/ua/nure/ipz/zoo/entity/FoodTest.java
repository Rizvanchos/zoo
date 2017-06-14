package ua.nure.ipz.zoo.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class FoodTest {

    private static final float DEFAULT_QUANTITY = 0;
    private static final float FOOD_QUANTITY = 10;
    private static final float FLOAT_DELTA = 0;

    @Mock
    private Product mockProduct;

    @Test
    public void shouldCreateFoodWithDefaultValues_whenCallDefaultConstructor() {
        Food food = new Food();
        assertNull(food.getProduct());
        assertEquals(DEFAULT_QUANTITY, food.getQuantity(), FLOAT_DELTA);
    }

    @Test
    public void shouldCreateFoodWithParameters_whenConstructorWithParameters() {
        Food food = new Food(mockProduct, FOOD_QUANTITY);
        assertEquals(mockProduct, food.getProduct());
        assertEquals(Float.valueOf(FOOD_QUANTITY), Float.valueOf(food.getQuantity()));
    }

    @Test
    public void shouldSetNewFoodQuantity_whenSetNewQuantity(){
        Food food = new Food();
        food.setQuantity(FOOD_QUANTITY);

        assertEquals(FOOD_QUANTITY, food.getQuantity(), FLOAT_DELTA);
    }

    @Test
    public void shouldSetNewFoodProduct_whenSetNewProduct(){
        Food food = new Food();
        food.setProduct(mockProduct);

        assertEquals(mockProduct, food.getProduct());
    }
}
