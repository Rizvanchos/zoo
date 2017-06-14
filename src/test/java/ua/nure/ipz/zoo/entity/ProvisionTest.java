package ua.nure.ipz.zoo.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ProvisionTest {

    private static final int DOUBLE_PRICE_COEFFICIENT = 2;
    private static final float FOOD_QUANTITY = 1;

    @Mock
    private Ration mockRation;
    @Mock
    private Food mockFood;

    private Provision provision = new Provision();

    @Before
    public void setUp() {
        when(mockRation.getFoods()).thenReturn(getFoodList());
        when(mockFood.getQuantity()).thenReturn(FOOD_QUANTITY);
        provision.getRations().clear();
    }

    private List<Food> getFoodList() {
        return Collections.singletonList(mockFood);
    }

    @Test
    public void shouldContainRation_whenAddRation() {
        provision.getRations().add(mockRation);
        assertTrue(provision.contains(mockRation));
    }

    @Test
    public void shouldReturnEmptyNeedsList_whenProvisionHasNoRations() {
        assertEquals(Collections.emptyList(), provision.getNeedsList());
    }

    @Test
    public void shouldReturnNeedsList_whenProvisionContainsRation() {
        provision.getRations().add(mockRation);
        assertEquals(getFoodList(), provision.getNeedsList());
    }

    @Test
    public void shouldSetNewProvisionRations_whenSetNewRations(){
        List<Ration> rations = Collections.singletonList(mockRation);
        provision.setRations(rations);

        assertEquals(rations, provision.getRations());
    }

    @Test
    public void shouldIncreaseFoodQuantity_whenRationsHaveSameFood(){
        List<Ration> rations = Arrays.asList(mockRation, mockRation);
        provision.setRations(rations);
        provision.getNeedsList();

        verify(mockFood).setQuantity(FOOD_QUANTITY * DOUBLE_PRICE_COEFFICIENT);
    }
}
