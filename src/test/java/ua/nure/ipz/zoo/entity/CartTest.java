package ua.nure.ipz.zoo.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Map;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ua.nure.ipz.zoo.exception.LockingEmptyCartException;
import ua.nure.ipz.zoo.exception.UnmodifiableCartException;

@RunWith(MockitoJUnitRunner.class)
public class CartTest {

    private static final TicketType TICKET_TYPE = TicketType.STANDARD;
    private static final BigDecimal TICKET_PRICE = BigDecimal.TEN;
    private static final int TICKET_QUANTITY = 5;

    @Mock
    private Ticket mockTicket;
    @Rule
    public ExpectedException exception = ExpectedException.none();

    private Cart cart = new Cart();

    @Before
    public void setUp() throws Exception {
        when(mockTicket.getType()).thenReturn(TICKET_TYPE);
        when(mockTicket.getPrice()).thenReturn(TICKET_PRICE);

        cart.setModifiable(true);
        cart.clear();
    }

    @Test
    public void shouldReturnZero_whenCalculateEmptyCart() {
        cart.setOrderedTickets(Collections.emptyMap());
        assertEquals(BigDecimal.ZERO, cart.getTotalPrice());
    }

    @Test
    public void shouldCalculateTotal_whenCartIsNotEmpty() {
        cart.setOrderedTickets(generateTicket());

        BigDecimal expectedTotalPrice = BigDecimal.valueOf(TICKET_PRICE.intValue() * TICKET_QUANTITY);
        assertEquals(expectedTotalPrice, cart.getTotalPrice());
    }

    private Map<Ticket, Integer> generateTicket() {
        return Collections.singletonMap(mockTicket, TICKET_QUANTITY);
    }

    @Test
    public void shouldThrowException_whenTryCheckoutWithEmptyCart() throws LockingEmptyCartException {
        cart.setOrderedTickets(Collections.emptyMap());

        exception.expect(LockingEmptyCartException.class);
        exception.expectMessage(String.valueOf(cart.getDomainId()));

        cart.checkout();
    }

    @Test
    public void shouldLockCart_whenPerformCheckout() throws LockingEmptyCartException {
        cart.setOrderedTickets(generateTicket());
        cart.checkout();

        assertFalse(cart.isModifiable());
    }

    @Test
    public void shouldThrowException_whenTryClearUnmodifiableCart() throws UnmodifiableCartException {
        cart.setModifiable(false);

        exception.expect(UnmodifiableCartException.class);
        exception.expectMessage(String.valueOf(cart.getDomainId()));

        cart.clear();
    }

    @Test
    public void shouldRemoveAllCartEntries_whenCallClear() throws UnmodifiableCartException {
        cart.setOrderedTickets(generateTicket());
        cart.clear();

        assertTrue(cart.getOrderedTickets().isEmpty());
    }
}
