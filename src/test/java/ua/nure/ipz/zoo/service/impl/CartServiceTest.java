package ua.nure.ipz.zoo.service.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import ua.nure.ipz.zoo.dto.CartDto;
import ua.nure.ipz.zoo.entity.TicketType;
import ua.nure.ipz.zoo.exception.LockingEmptyCartException;
import ua.nure.ipz.zoo.exception.NotTicketTypeException;
import ua.nure.ipz.zoo.exception.RemovingUnexistingItemException;
import ua.nure.ipz.zoo.exception.ServiceValidationException;
import ua.nure.ipz.zoo.exception.UnmodifiableCartException;
import ua.nure.ipz.zoo.service.CartService;
import ua.nure.ipz.zoo.service.TicketService;

import java.math.BigDecimal;
import java.util.UUID;

import static org.hamcrest.Matchers.empty;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ua.nure.ipz.zoo.config.ZooJUnitConfiguration.class)
@Transactional
public class CartServiceTest {

    private static final int NEW_QUANTITY = 10;
    private static final int INVALID_QUANTITY = 0;
    private static final int QUANTITY = 3;

    @Autowired
    private CartService cartService;
    @Autowired
    private TicketService ticketService;

    private UUID firstTicketId;
    private UUID secondTicketId;
    private UUID cartId;

    @Before
    public void setUp() throws NotTicketTypeException {
        cartId = cartService.create();
        firstTicketId = ticketService.create(TicketType.STUDENT.toString(), BigDecimal.ONE);
        secondTicketId = ticketService.create(TicketType.STANDARD.toString(), BigDecimal.TEN);
    }

    @Test
    public void shouldCreateNewCart_whenCallCreate() {
        assertNotNull(cartId);
    }

    @Test(expected = ServiceValidationException.class)
    public void shouldThrowException_whenAddItemToNullCart() throws UnmodifiableCartException {
        cartService.addItem(null, firstTicketId, QUANTITY);
    }

    @Test(expected = ServiceValidationException.class)
    public void shouldThrowException_whenAddItemWithNullProduct() throws UnmodifiableCartException {
        cartService.addItem(cartId, null, QUANTITY);
    }

    @Test(expected = ServiceValidationException.class)
    public void shouldThrowException_whenAddItemWithInvalidQuantity() throws UnmodifiableCartException {
        cartService.addItem(cartId, firstTicketId, INVALID_QUANTITY);
    }

    @Test
    public void shouldAddItemToCart_whenPassValidParameters() throws UnmodifiableCartException {
        cartService.addItem(cartId, firstTicketId, QUANTITY);
        assertTrue(isCartContentMatchesQuantity(QUANTITY));
    }

    private boolean isCartContentMatchesQuantity(int quantity) {
        CartDto cartDto = cartService.view(cartId);
        return cartDto.getItems()
                .entrySet()
                .stream()
                .allMatch(e -> e.getKey().getDomainId().equals(firstTicketId) && e.getValue().equals(quantity));
    }

    @Test(expected = ServiceValidationException.class)
    public void shouldThrowException_whenUpdateItemToNullCart() throws UnmodifiableCartException {
        cartService.updateItem(null, firstTicketId, QUANTITY);
    }

    @Test(expected = ServiceValidationException.class)
    public void shouldThrowException_whenUpdateItemWithNullProduct() throws UnmodifiableCartException {
        cartService.updateItem(cartId, null, QUANTITY);
    }

    @Test(expected = ServiceValidationException.class)
    public void shouldThrowException_whenUpdateItemWithInvalidQuantity() throws UnmodifiableCartException {
        cartService.updateItem(cartId, firstTicketId, INVALID_QUANTITY);
    }

    @Test
    public void shouldUpdateItemToRation_whenPassValidParameters() throws UnmodifiableCartException {
        cartService.addItem(cartId, firstTicketId, QUANTITY);
        cartService.updateItem(cartId, firstTicketId, NEW_QUANTITY);

        assertTrue(isCartContentMatchesQuantity(NEW_QUANTITY));
    }

    @Test(expected = ServiceValidationException.class)
    public void shouldThrowException_whenRemoveItemToNullCart() throws UnmodifiableCartException, RemovingUnexistingItemException {
        cartService.removeItem(null, firstTicketId);
    }

    @Test(expected = ServiceValidationException.class)
    public void shouldThrowException_whenRemoveItemWithNullProduct() throws RemovingUnexistingItemException, UnmodifiableCartException {
        cartService.removeItem(cartId, null);
    }

    @Test(expected = ServiceValidationException.class)
    public void shouldThrowException_whenRemovingUnexistingItem() throws RemovingUnexistingItemException, UnmodifiableCartException {
        cartService.removeItem(cartId, UUID.randomUUID());
    }

    @Test
    public void shouldRemoveItemFromRation_whenCallRemoveItem() throws UnmodifiableCartException, RemovingUnexistingItemException {
        cartService.addItem(cartId, firstTicketId, QUANTITY);
        cartService.removeItem(cartId, firstTicketId);

        CartDto cartDto = cartService.view(cartId);
        assertThat(cartDto.getItems().keySet(), empty());
    }

    @Test
    public void shouldLockCart_whenCallLock() throws LockingEmptyCartException, UnmodifiableCartException, NotTicketTypeException {
        cartService.addItem(cartId, firstTicketId, QUANTITY);
        cartService.lock(cartId);

        CartDto cartDto = cartService.view(cartId);
        assertEquals(Boolean.FALSE, cartDto.isModifiable());
    }

    @Test
    public void shouldClearCart_whenCallClear() throws UnmodifiableCartException {
        cartService.addItem(cartId, firstTicketId, QUANTITY);
        cartService.addItem(cartId, secondTicketId, QUANTITY);

        cartService.clear(cartId);

        CartDto cartDto = cartService.view(cartId);
        assertTrue(cartDto.getItems().isEmpty());
    }
}
