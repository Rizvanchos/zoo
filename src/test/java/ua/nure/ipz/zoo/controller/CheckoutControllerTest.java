package ua.nure.ipz.zoo.controller;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import ua.nure.ipz.zoo.controller.constant.ControllerConstants;
import ua.nure.ipz.zoo.controller.constant.SessionKeys;
import ua.nure.ipz.zoo.controller.constant.ViewNames;
import ua.nure.ipz.zoo.dto.CartDto;
import ua.nure.ipz.zoo.dto.TicketDto;
import ua.nure.ipz.zoo.service.CartService;
import ua.nure.ipz.zoo.service.OrderService;

@RunWith(MockitoJUnitRunner.class)
public class CheckoutControllerTest {

    private static final UUID ORDER_UUID = UUID.randomUUID();
    private static final UUID CART_UUID = UUID.randomUUID();

    private static final int TICKET_QUANTITY = 10;

    private static final String CONTACT_NAME = "contactName";
    private static final String CONTACT_EMAIL = "contactEmail";
    private static final String CONTACT_PHONE = "contactPhone";
    private static final String COMMENT = "comment";

    private static final String CHECKOUT_URL = "/checkout/";
    private static final String ORDER_URL = "/order/";

    @Mock
    private CartDto mockCartDto;
    @Mock
    private TicketDto mockTicketDto;
    @Mock
    private CartService mockCartService;
    @Mock
    private OrderService mockOrderService;

    @InjectMocks
    private CheckoutController checkoutController;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/jsp/view/");
        viewResolver.setSuffix(".jsp");

        mockMvc = MockMvcBuilders.standaloneSetup(checkoutController).setViewResolvers(viewResolver).build();

        when(mockCartDto.getDomainId()).thenReturn(CART_UUID);
        when(mockCartService.create()).thenReturn(CART_UUID);
        when(mockCartService.view(CART_UUID)).thenReturn(mockCartDto);
    }

    @Test
    public void shouldDoCheckout_whenHaveNoCart() throws Exception {
        mockMvc.perform(get(CHECKOUT_URL))
                .andExpect(status().isOk())
                .andExpect(view().name(ViewNames.CHECKOUT))
                .andExpect(model().attribute(ControllerConstants.CheckoutView.CART, mockCartDto))
                .andExpect(request().sessionAttribute(SessionKeys.CART_ID, CART_UUID));

        verify(mockCartService).create();
        verify(mockCartService).view(CART_UUID);
    }

    @Test
    public void shouldDoCheckout_whenHaveCart() throws Exception {
        mockMvc.perform(get(CHECKOUT_URL)
                .sessionAttr(SessionKeys.CART_ID, CART_UUID))
                .andExpect(status().isOk()).andExpect(view().name(ViewNames.CHECKOUT))
                .andExpect(model().attribute(ControllerConstants.CheckoutView.CART, mockCartDto))
                .andExpect(request().sessionAttribute(SessionKeys.CART_ID, CART_UUID));

        verify(mockCartService, never()).create();
        verify(mockCartService).view(CART_UUID);
    }

    @Test
    public void shouldRedirect_whenCartIsEmpty() throws Exception {
        mockMvc.perform(post(CHECKOUT_URL)
                .sessionAttr(SessionKeys.CART_ID, CART_UUID)
                .param(CONTACT_NAME, CONTACT_NAME)
                .param(CONTACT_EMAIL, CONTACT_EMAIL)
                .param(CONTACT_PHONE, CONTACT_PHONE)
                .param(COMMENT, COMMENT))
                .andExpect(redirectedUrl(CHECKOUT_URL));
    }

    @Test
    public void shouldDoCheckout_whenCartIsNotEmpty() throws Exception {
        when(mockCartDto.getItems()).thenReturn(getCartItems());
        when(mockOrderService.create(CONTACT_NAME, CONTACT_EMAIL, CONTACT_PHONE, COMMENT, CART_UUID)).thenReturn(ORDER_UUID);

        mockMvc.perform(post(CHECKOUT_URL)
                .sessionAttr(SessionKeys.CART_ID, CART_UUID)
                .param(CONTACT_NAME, CONTACT_NAME)
                .param(CONTACT_EMAIL, CONTACT_EMAIL)
                .param(CONTACT_PHONE, CONTACT_PHONE)
                .param(COMMENT, COMMENT))
                .andExpect(redirectedUrl(ORDER_URL))
                .andExpect(request().sessionAttribute(SessionKeys.CART_ID, nullValue()))
                .andExpect(request().sessionAttribute(SessionKeys.ORDER_ID, ORDER_UUID));

        verify(mockCartService).view(CART_UUID);
        verify(mockCartService).lock(CART_UUID);
        verify(mockOrderService).create(CONTACT_NAME, CONTACT_EMAIL, CONTACT_PHONE, COMMENT, CART_UUID);
    }

    private Map<TicketDto, Integer> getCartItems() {
        return Collections.singletonMap(mockTicketDto, TICKET_QUANTITY);
    }
}
