package ua.nure.ipz.zoo.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ua.nure.ipz.zoo.exception.OrderLifecycleException;

import java.util.Collections;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class OperatorAccountTest {

    private static final String NAME = "Name";
    private static final String EMAIL = "Email";
    private static final String PASSWORD = "Password";

    @Mock
    private Order mockOrder;
    @Rule
    public ExpectedException exception = ExpectedException.none();

    private OperatorAccount operatorAccount = new OperatorAccount();

    @Before
    public void setUp() throws OrderLifecycleException {
        doNothing().when(mockOrder).process();
        doNothing().when(mockOrder).finish();
    }

    @Test
    public void shouldCreateOperatorAccountWithParameters_whenCallConstructorWithParameters(){
        OperatorAccount operatorAccount = new OperatorAccount(NAME, EMAIL, PASSWORD);

        assertEquals(NAME, operatorAccount.getName());
        assertEquals(EMAIL, operatorAccount.getEmail());
        assertEquals(PASSWORD, operatorAccount.getPassword());
    }

    @Test
    public void shouldSetNewOrdersToZooManager_whenSetNewOrders(){
        OperatorAccount operatorAccount = new OperatorAccount();
        List<Order> orders = Collections.singletonList(mockOrder);
        operatorAccount.setOrders(orders);

        assertEquals(orders, operatorAccount.getOrders());
    }

    @Test
    public void shouldAssignOrderToOperator_whenPerformTrackOrder() {
        operatorAccount.trackOrder(mockOrder);
        assertTrue(operatorAccount.getOrders().contains(mockOrder));
    }

    @Test
    public void shouldProcessAndFinishOrderProcess_whenProcessOrder() throws OrderLifecycleException {
        operatorAccount.processOrder(mockOrder);
        verify(mockOrder).process();
        verify(mockOrder).finish();
    }

    @Test
    public void shouldProcessOrderFirstAndThenFinish_whenProcessOrder() throws OrderLifecycleException {
        InOrder inOrder = inOrder(mockOrder);
        operatorAccount.processOrder(mockOrder);

        inOrder.verify(mockOrder).process();
        inOrder.verify(mockOrder).finish();
    }
}
