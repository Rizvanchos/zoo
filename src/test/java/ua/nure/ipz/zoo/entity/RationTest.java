package ua.nure.ipz.zoo.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class RationTest {

    @Mock
    private Food mockFood;
    @Mock
    private Animal mockAnimal;

    @Test
    public void shouldCreateRationWithDefaultValues_whenCallDefaultConstructor(){
        Ration ration = new Ration();
        assertNull(ration.getAnimal());
        assertTrue(ration.getFoods().isEmpty());
    }

    @Test
    public void shouldCreateRationWithPassedAnimal_whenPassAnimal() {
        Ration ration = new Ration(mockAnimal);
        assertEquals(mockAnimal, ration.getAnimal());
    }

    @Test
    public void shouldSetNewRationAnimal_whenSetNewAnimal(){
        Ration ration = new Ration();
        ration.setAnimal(mockAnimal);

        assertEquals(mockAnimal, ration.getAnimal());
    }

    @Test
    public void shouldSetNewRationFoods_whenSetNewFoods(){
        Ration ration = new Ration();
        List<Food> foods = Collections.singletonList(mockFood);
        ration.setFoods(foods);

        assertEquals(foods, ration.getFoods());
    }
}
