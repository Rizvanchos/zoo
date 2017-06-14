package ua.nure.ipz.zoo.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ua.nure.ipz.zoo.exception.ModifiableCartException;
import ua.nure.ipz.zoo.exception.OrderLifecycleException;

@RunWith(MockitoJUnitRunner.class)
public class OrderTest {

    private static final String ORDER_LIFECYCLE_EXCEPTION_MESSAGE = "Order #%s must have %s status, while %s is a current status";
    private static final String MODIFIABLE_CART_EXCEPTION_MESSAGE = "Cart #%s must be locked by this moment";
    private static final LocalDateTime ORDER_PLACEMENT_TIME = LocalDateTime.now();
    private static final BigDecimal CART_TOTAL_PRICE = BigDecimal.TEN;
    private static final UUID ORDER_UUID = UUID.randomUUID();
    private static final String ORDER_COMMENT = "Comment";

    @Mock
    private Cart mockCart;
    @Mock
    private Contact mockContact;
    @Mock
    private Discount mockDiscount;
    @Mock
    private PaymentInfo mockPaymentInfo;
    @Rule
    public ExpectedException exception = ExpectedException.none();

    private Order order;

    @Before
    public void setUp() throws ModifiableCartException {
        when(mockCart.getTotalPrice()).thenReturn(CART_TOTAL_PRICE);

        order = spy(generateOrder());
        when(order.getDomainId()).thenReturn(ORDER_UUID);
    }

    private Order generateOrder() throws ModifiableCartException {
        return new Order(mockCart, mockContact, ORDER_PLACEMENT_TIME);
    }

    @Test
    public void shouldCreateOrderWithDefaultValues_whenCallDefaultConstructor(){
        Order order = new Order();

        assertNull(order.getCart());
        assertNull(order.getBasicPrice());
        assertNull(order.getContact());
        assertNull(order.getStatus());
        assertNull(order.getComment());
        assertNull(order.getDiscount());
        assertNull(order.getTotalPrice());
        assertNull(order.getPaymentInfo());
        assertNull(order.getPlacementTime());
    }

    @Test
    public void shouldSetNewOrderPaymentInfo_whenSetNewPaymentInfo(){
        Order order = new Order();
        order.setPaymentInfo(mockPaymentInfo);

        assertEquals(mockPaymentInfo, order.getPaymentInfo());
    }

    @Test
    public void shouldSetNewOrderComment_whenSetNewComment() {
        Order order = new Order();
        order.setComment(ORDER_COMMENT);

        assertEquals(ORDER_COMMENT, order.getComment());
    }

    @Test
    public void shouldSetNewOrderPlacementTime_whenSetNewPlacementTime(){
        Order order = new Order();
        order.setPlacementTime(ORDER_PLACEMENT_TIME);

        assertEquals(ORDER_PLACEMENT_TIME, order.getPlacementTime());
    }

    @Test
    public void shouldThrowExceptionDuringOrderCreation_whenCartIsModifiable() throws ModifiableCartException {
        UUID cartUUID = UUID.randomUUID();

        when(mockCart.getDomainId()).thenReturn(cartUUID);
        when(mockCart.isModifiable()).thenReturn(true);

        exception.expect(ModifiableCartException.class);
        exception.expectMessage(String.format(MODIFIABLE_CART_EXCEPTION_MESSAGE, cartUUID));

        generateOrder();
    }

    @Test
    public void shouldProperlyCreate_whenCartIsLocked() throws ModifiableCartException {
        order = generateOrder();

        assertEquals(order.getCart(), mockCart);
        assertEquals(order.getContact(), mockContact);
        assertEquals(order.getStatus(), OrderStatus.ACCEPTED);
        assertEquals(order.getBasicPrice(), CART_TOTAL_PRICE);
    }

    @Test
    public void shouldReturnCartTotalPrice_whenGetBasicPrice() {
        assertEquals(CART_TOTAL_PRICE, order.getBasicPrice());
    }

    @Test
    public void shouldReturnDiscountedPrice_whenDiscountIsPresent() {
        order.setDiscount(mockDiscount);
        order.getTotalPrice();

        verify(mockDiscount).discountPrice(mockCart);
    }

    @Test
    public void shouldNotDiscountPrice_whenDiscountIsNotPresent() {
        assertEquals(CART_TOTAL_PRICE, order.getTotalPrice());
    }

    @Test
    public void shouldThrowException_whenProcessOrderWithNotAcceptedStatus() throws OrderLifecycleException {
        exception.expect(OrderLifecycleException.class);
        exception.expectMessage(String.format(ORDER_LIFECYCLE_EXCEPTION_MESSAGE, ORDER_UUID, OrderStatus.ACCEPTED.toString(), OrderStatus.CANCELLED.toString()));

        order.cancel();
        order.process();
    }

    @Test
    public void shouldChangeOrderStatusFromAcceptedToProcessing_whenPerformProcess() throws OrderLifecycleException {
        order.process();
        assertEquals(OrderStatus.PROCESSING, order.getStatus());
    }

    @Test
    public void shouldThrowException_whenFinishOrderWithNotProcessingStatus() throws OrderLifecycleException {
        exception.expect(OrderLifecycleException.class);
        exception.expectMessage(String.format(ORDER_LIFECYCLE_EXCEPTION_MESSAGE, ORDER_UUID, OrderStatus.PROCESSING.toString(), OrderStatus.ACCEPTED.toString()));

        order.finish();
    }

    @Test
    public void shouldChangeOrderStatusFromProcessingToFinished_whenPerformFinish() throws OrderLifecycleException {
        order.process();
        order.finish();
        assertEquals(OrderStatus.FINISHED, order.getStatus());
    }

    @Test
    public void shouldChangeOrderStatusToCancelled_whenPerformCancel() {
        order.cancel();
        assertEquals(OrderStatus.CANCELLED, order.getStatus());
    }
}
