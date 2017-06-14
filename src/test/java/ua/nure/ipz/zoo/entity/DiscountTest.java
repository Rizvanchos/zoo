package ua.nure.ipz.zoo.entity;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DiscountTest {

    private static final TicketType TICKET_TYPE_WITH_DISCOUNT = TicketType.STUDENT;
    private static final TicketType TICKET_TYPE_WITHOUT_DISCOUNT = TicketType.STANDARD;
    private static final BigDecimal CART_TOTAL_PRICE = BigDecimal.TEN;
    private static final int TICKET_QUANTITY_BIGGER_THEN_DISCOUNT_BARRIER = 5;
    private static final int TICKET_QUANTITY_LESS_THEN_DISCOUNT_BARRIER = 1;
    private static float DISCOUNT_COEFFICIENT = 0.25f;
    private static int DISCOUNT_BARRIER = 3;
    private static final TicketType DEFAULT_TICKET_TYPE = TicketType.STANDARD;
    private static final int DEFAULT_BARRIER = 10;
    private static final float DEFAULT_COEFFICIENT = 0.7f;
    private static final float FLOAT_DELTA = 0;

    @Mock
    private Cart mockCart;
    @Mock
    private Ticket mockTicket;

    private Discount discount = new Discount(TICKET_TYPE_WITH_DISCOUNT, DISCOUNT_BARRIER, DISCOUNT_COEFFICIENT);

    @Before
    public void setUp() {
        when(mockTicket.getType()).thenReturn(TICKET_TYPE_WITH_DISCOUNT);
        when(mockCart.getTotalPrice()).thenReturn(CART_TOTAL_PRICE);
    }

    @Test
    public void shouldCreateDiscountWithDefaultValues_whenCallDefaultConstructor(){
        Discount discount = new Discount();
        assertEquals(DEFAULT_TICKET_TYPE, discount.getType());
        assertEquals(DEFAULT_BARRIER, discount.getBarrier());
        assertEquals(DEFAULT_COEFFICIENT, discount.getCoefficient(), FLOAT_DELTA);
    }

    @Test
    public void shouldSetNewDiscountType_whenSetNewType(){
        Discount discount = new Discount();
        discount.setType(TICKET_TYPE_WITH_DISCOUNT);

        assertEquals(TICKET_TYPE_WITH_DISCOUNT, discount.getType());
    }

    @Test
    public void shouldSetNewDiscountBarrier_whenSetNewBarrier(){
        Discount discount = new Discount();
        discount.setBarrier(DISCOUNT_BARRIER);

        assertEquals(DISCOUNT_BARRIER, discount.getBarrier());
    }

    @Test
    public void shouldSetNewDiscountCoefficient_whenSetNewCoefficient(){
        Discount discount = new Discount();
        discount.setCoefficient(DISCOUNT_COEFFICIENT);

        assertEquals(DISCOUNT_COEFFICIENT, discount.getCoefficient(), FLOAT_DELTA);
    }

    @Test
    public void shouldNotDiscountPrice_whenTicketTypeForDiscountAreNotMatch() {
        when(mockTicket.getType()).thenReturn(TICKET_TYPE_WITHOUT_DISCOUNT);
        when(mockCart.getOrderedTickets()).thenReturn(getOrderedTickets(TICKET_QUANTITY_BIGGER_THEN_DISCOUNT_BARRIER));
        assertEquals(CART_TOTAL_PRICE, discount.discountPrice(mockCart));
    }

    @Test
    public void shouldDiscountPrice_whenCartQuantityIsBiggerThenDiscountBarrier() {
        when(mockCart.getOrderedTickets()).thenReturn(getOrderedTickets(TICKET_QUANTITY_BIGGER_THEN_DISCOUNT_BARRIER));

        BigDecimal expectedDiscountPrice = CART_TOTAL_PRICE.multiply(BigDecimal.valueOf(DISCOUNT_COEFFICIENT));
        assertEquals(expectedDiscountPrice, discount.discountPrice(mockCart));
    }

    private Map<Ticket, Integer> getOrderedTickets(int quantity) {
        return Collections.singletonMap(mockTicket, quantity);
    }

    @Test
    public void shouldNotDiscountPrice_whenCartQuantityIsLessThenDiscountBarrier() {
        when(mockCart.getOrderedTickets()).thenReturn(getOrderedTickets(TICKET_QUANTITY_LESS_THEN_DISCOUNT_BARRIER));
        assertEquals(CART_TOTAL_PRICE, discount.discountPrice(mockCart));
    }
}
