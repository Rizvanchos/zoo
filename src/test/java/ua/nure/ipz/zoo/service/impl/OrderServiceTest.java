package ua.nure.ipz.zoo.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import ua.nure.ipz.zoo.dto.OrderDto;
import ua.nure.ipz.zoo.entity.OrderStatus;
import ua.nure.ipz.zoo.exception.DuplicateNamedEntityException;
import ua.nure.ipz.zoo.exception.LockingEmptyCartException;
import ua.nure.ipz.zoo.exception.ModifiableCartException;
import ua.nure.ipz.zoo.exception.NotTicketTypeException;
import ua.nure.ipz.zoo.exception.OrderLifecycleException;
import ua.nure.ipz.zoo.exception.ServiceValidationException;
import ua.nure.ipz.zoo.exception.UnmodifiableCartException;
import ua.nure.ipz.zoo.service.AccountService;
import ua.nure.ipz.zoo.service.CartService;
import ua.nure.ipz.zoo.service.OrderService;
import ua.nure.ipz.zoo.service.TicketService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ua.nure.ipz.zoo.config.ZooJUnitConfiguration.class)
@Transactional
public class OrderServiceTest {

    private static final String NAME = "Name";
    private static final String EMAIL = "Email@Email.ru";
    private static final String INVALID_EMAIL = "InvalidEmail";
    private static final String CONTACT_PHONE = "(050)123-45-67";
    private static final String COMMENT = "Comment";

    private static final BigDecimal TICKET_PRICE = BigDecimal.ONE;
    private static final String TICKET_TYPE = "STANDARD";

    private static final String EMPTY_STRING = "";
    private static final int QUANTITY = 20;

    private static final String PASSWORD = "Password";

    private static final int BIG_DECIMAL_SCALE = 2;
    private static final float COEFFICIENT = 0.25f;
    private static final int COEFFICIENT_OUT_OF_RANGE = 10;
    private static final int BARRIER = 10;
    private static final int BARRIER_LESS_THEN_MIN = 0;

    private static final String CARD_TYPE = "Visa";
    private static final String CARD_NUMBER = "4111111111111111";
    private static final String CARD_CVV = "123";
    private static final String CVV_LENGTH_GREATER_THEN_MAXIMUM = "12345";
    private static final String CVV_LENGTH_LESS_THEN_MINIMUM = "12";
    private static final String INVALID_CARD_NUMBER = "123456";

    @Autowired
    private OrderService orderService;
    @Autowired
    private CartService cartService;
    @Autowired
    private TicketService ticketService;
    @Autowired
    private AccountService accountService;

    private UUID orderId;
    private UUID cartId;

    @Before
    public void setUp() throws ModifiableCartException, NotTicketTypeException, UnmodifiableCartException, LockingEmptyCartException {
        UUID ticketId = ticketService.create(TICKET_TYPE, TICKET_PRICE);

        cartId = cartService.create();
        cartService.addItem(cartId, ticketId, QUANTITY);
        orderId = getOrder(cartId);
    }

    private UUID getOrder(UUID cartId) throws ModifiableCartException, LockingEmptyCartException {
        cartService.lock(cartId);
        return orderService.create(NAME, EMAIL, CONTACT_PHONE, COMMENT, cartId);
    }

    @Test
    public void shouldCreateOrder_whenPassValidParameters() {
        OrderDto orderDto = orderService.view(orderId);
        assertOrderDtoMatches(orderDto);
    }

    private void assertOrderDtoMatches(OrderDto orderDto) {
        assertEquals(NAME, orderDto.getCustomerName());
        assertEquals(EMAIL, orderDto.getCustomerEmail());
        assertEquals(CONTACT_PHONE, orderDto.getCustomerPhone());
        assertEquals(COMMENT, orderDto.getComment());
        assertEquals(getOrderPrice(), orderDto.getBasicPrice());
        assertEquals(getOrderPrice(), orderDto.getTotalPrice());
        assertEquals(OrderStatus.ACCEPTED.toString(), orderDto.getOrderStatus());
    }

    private BigDecimal getOrderPrice() {
        return TICKET_PRICE.multiply(BigDecimal.valueOf(QUANTITY)).setScale(0);
    }

    @Test(expected = ServiceValidationException.class)
    public void shouldThrowException_whenCreateOrderWithEmptyName() throws ModifiableCartException {
        orderService.create(EMPTY_STRING, EMAIL, CONTACT_PHONE, COMMENT, cartId);
    }

    @Test(expected = ServiceValidationException.class)
    public void shouldThrowException_whenCreateOrderWithInvalidEmail() throws ModifiableCartException {
        orderService.create(NAME, INVALID_EMAIL, CONTACT_PHONE, COMMENT, cartId);
    }

    @Test(expected = ServiceValidationException.class)
    public void shouldThrowException_whenCreateOrderWithEmptyContactPhone() throws ModifiableCartException {
        orderService.create(NAME, INVALID_EMAIL, EMPTY_STRING, COMMENT, cartId);
    }

    @Test(expected = ServiceValidationException.class)
    public void shouldThrowException_whenCreateOrderWithNotLockedCart() throws ModifiableCartException {
        UUID notLockedCart = cartService.create();
        orderService.create(NAME, INVALID_EMAIL, CONTACT_PHONE, COMMENT, notLockedCart);
    }

    @Test
    public void shouldSetDiscount_whenSetDiscountWithValidParameters() {
        orderService.setDiscount(orderId, TICKET_TYPE, BARRIER, COEFFICIENT);
        assertTrue(isOrderHasDiscountedPrice());
    }

    private boolean isOrderHasDiscountedPrice() {
        OrderDto orderDto = orderService.view(orderId);
        return getDiscountedPrice().equals(orderDto.getTotalPrice());
    }

    private BigDecimal getDiscountedPrice() {
        return TICKET_PRICE.multiply(BigDecimal.valueOf(COEFFICIENT * QUANTITY)).setScale(BIG_DECIMAL_SCALE);
    }

    @Test
    public void shouldFindOrderWithGiveId_whenCallFindOrder() {
        assertOrderDtoMatches(orderService.findOrder(orderId));
    }

    @Test(expected = ServiceValidationException.class)
    public void shouldThrowException_whenSetDiscountWithEmptyTicketType() {
        orderService.setDiscount(orderId, EMPTY_STRING, BARRIER, COEFFICIENT);
    }

    @Test(expected = ServiceValidationException.class)
    public void shouldThrowException_whenSetDiscountWithBarrierLessThenOne() {
        orderService.setDiscount(orderId, TICKET_TYPE, BARRIER_LESS_THEN_MIN, COEFFICIENT);
    }

    @Test(expected = ServiceValidationException.class)
    public void shouldThrowException_whenSetDiscountWithCoefficientOutOfRange() {
        orderService.setDiscount(orderId, TICKET_TYPE, BARRIER, COEFFICIENT_OUT_OF_RANGE);
    }

    @Test
    public void shouldNotThrowException_whenSetPaymentInfoWithValidParameters() {
        orderService.setPaymentInfo(orderId, CARD_TYPE, CARD_NUMBER, CARD_CVV);
    }

    @Test(expected = ServiceValidationException.class)
    public void shouldThrowException_whenSetPaymentInfoWithEmptyCardType() {
        orderService.setPaymentInfo(orderId, EMPTY_STRING, CARD_NUMBER, CARD_CVV);
    }

    @Test(expected = ServiceValidationException.class)
    public void shouldThrowException_whenSetPaymentInfoWithInvalidCardNumber() {
        orderService.setPaymentInfo(orderId, CARD_TYPE, INVALID_CARD_NUMBER, CARD_CVV);
    }

    @Test(expected = ServiceValidationException.class)
    public void shouldThrowException_whenSetPaymentInfoWithCvvLengthLessThenMinimum() {
        orderService.setPaymentInfo(orderId, CARD_TYPE, CARD_NUMBER, CVV_LENGTH_LESS_THEN_MINIMUM);
    }

    @Test(expected = ServiceValidationException.class)
    public void shouldThrowException_whenSetPaymentInfoWithCvvLengthLessGreaterThenMaximum() {
        orderService.setPaymentInfo(orderId, CARD_TYPE, CARD_NUMBER, CVV_LENGTH_GREATER_THEN_MAXIMUM);
    }

    @Test
    public void shouldProcessOrder_whenCallProcess() throws DuplicateNamedEntityException, OrderLifecycleException {
        UUID operatorId = getOperatorAccount();
        orderService.process(orderId, operatorId);

        OrderDto orderDto = orderService.view(orderId);
        assertEquals(OrderStatus.PROCESSING.toString(), orderDto.getOrderStatus());
    }

    private UUID getOperatorAccount() throws DuplicateNamedEntityException {
        return accountService.createOperator(NAME, EMAIL, PASSWORD);
    }

    @Test(expected = OrderLifecycleException.class)
    public void shouldThrowException_whenProcessCanceledOrder() throws OrderLifecycleException, DuplicateNamedEntityException {
        orderService.cancel(orderId);
        orderService.process(orderId, getOperatorAccount());
    }

    @Test
    public void shouldCancelOrder_whenCallCancel() throws OrderLifecycleException {
        orderService.cancel(orderId);

        OrderDto orderDto = orderService.view(orderId);
        assertEquals(OrderStatus.CANCELLED.toString(), orderDto.getOrderStatus());
    }
}
