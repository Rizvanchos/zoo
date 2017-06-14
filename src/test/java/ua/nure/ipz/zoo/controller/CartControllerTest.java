package ua.nure.ipz.zoo.controller;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
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

@RunWith(MockitoJUnitRunner.class)
public class CartControllerTest {

    private static final UUID TICKET_UUID = UUID.randomUUID();
    private static final UUID NEW_CART_UUID = UUID.randomUUID();
    private static final UUID STORED_CART_UUID = UUID.randomUUID();

    private static final int TICKET_QUANTITY = 10;

    private static final String CART_ITEMS_ATTRIBUTE = "items";
    private static final String TICKET_ID_PARAMETER = "ticketId";
    private static final String QUANTITY_PARAMETER = "quantity";
    private static final String NEW_QUANTITY_PARAMETER = "newQuantity";

    private static final String CART_ROOT_URL = "/cart";
    private static final String CART_CLEAR_URL = CART_ROOT_URL + "/clear";
    private static final String CART_ADD_ITEM_URL = CART_ROOT_URL + "/additem";
    private static final String CART_SET_ITEM_URL = CART_ROOT_URL + "/setitem";
    private static final String CART_REMOVE_ITEM_URL = CART_ROOT_URL + "/removeitem";

    @Mock
    private CartDto mockCartDto;
    @Mock
    private TicketDto mockTicketDto;
    @Mock
    private CartService mockCartService;
    @InjectMocks
    private CartController cartController;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/jsp/view/");
        viewResolver.setSuffix(".jsp");

        mockMvc = MockMvcBuilders.standaloneSetup(cartController).setViewResolvers(viewResolver).build();

        when(mockCartService.view(STORED_CART_UUID)).thenReturn(mockCartDto);
        when(mockCartService.viewPurchases(STORED_CART_UUID)).thenReturn(getCartItems());
    }

    private Map<TicketDto, Integer> getCartItems() {
        return Collections.singletonMap(mockTicketDto, TICKET_QUANTITY);
    }

    @Test
    public void shouldCreateNewCart_whenCartNotInStoredSession() throws Exception {
        when(mockCartService.create()).thenReturn(NEW_CART_UUID);
        when(mockCartService.view(NEW_CART_UUID)).thenReturn(mockCartDto);
        when(mockCartService.viewPurchases(NEW_CART_UUID)).thenReturn(getCartItems());

        mockMvc.perform(get(CART_ROOT_URL))
                .andExpect(status().isOk())
                .andExpect(view().name(ViewNames.CART))
                .andExpect(model().attribute(ControllerConstants.CartView.CART, mockCartDto))
                .andExpect(model().attribute(CART_ITEMS_ATTRIBUTE, getCartItems()))
                .andExpect(request().sessionAttribute(SessionKeys.CART_ID, NEW_CART_UUID));

        verify(mockCartService).create();
        verify(mockCartService).view(NEW_CART_UUID);
        verify(mockCartService).viewPurchases(NEW_CART_UUID);
    }

    @Test
    public void shouldTakeCartFromSession_whenCartStoredInSession() throws Exception {
        mockMvc.perform(get(CART_ROOT_URL)
                .sessionAttr(SessionKeys.CART_ID, STORED_CART_UUID))
                .andExpect(status().isOk())
                .andExpect(view().name(ViewNames.CART))
                .andExpect(model().attribute(CART_ITEMS_ATTRIBUTE, getCartItems()))
                .andExpect(model().attribute(ControllerConstants.CartView.CART, mockCartDto));

        verify(mockCartService, never()).create();
    }

    @Test
    public void shouldAddItemToCart_whenAddItem() throws Exception {
        when(mockCartService.view(STORED_CART_UUID)).thenReturn(mockCartDto);

        mockMvc.perform(post(CART_ADD_ITEM_URL)
                .sessionAttr(SessionKeys.CART_ID, STORED_CART_UUID)
                .param(TICKET_ID_PARAMETER, TICKET_UUID.toString())
                .param(QUANTITY_PARAMETER, String.valueOf(TICKET_QUANTITY)))
                .andExpect(status().isCreated())
                .andExpect(request().sessionAttribute(SessionKeys.CART_ID, STORED_CART_UUID));

        verify(mockCartService).addItem(STORED_CART_UUID, TICKET_UUID, TICKET_QUANTITY);
    }

    @Test
    public void shouldSetItemToCart_whenSetItem() throws Exception {
        when(mockCartService.view(STORED_CART_UUID)).thenReturn(mockCartDto);

        mockMvc.perform(post(CART_SET_ITEM_URL)
                .sessionAttr(SessionKeys.CART_ID, STORED_CART_UUID)
                .param(TICKET_ID_PARAMETER, TICKET_UUID.toString())
                .param(NEW_QUANTITY_PARAMETER, String.valueOf(TICKET_QUANTITY)))
                .andExpect(status().isOk())
                .andExpect(request().sessionAttribute(SessionKeys.CART_ID, STORED_CART_UUID));

        verify(mockCartService).updateItem(STORED_CART_UUID, TICKET_UUID, TICKET_QUANTITY);
    }

    @Test
    public void shouldRemoveItemFromCart_whenRemoveItem() throws Exception {
        when(mockCartService.view(STORED_CART_UUID)).thenReturn(mockCartDto);

        mockMvc.perform(post(CART_REMOVE_ITEM_URL)
                .sessionAttr(SessionKeys.CART_ID, STORED_CART_UUID)
                .param(TICKET_ID_PARAMETER, TICKET_UUID.toString()))
                .andExpect(status().isOk())
                .andExpect(request().sessionAttribute(SessionKeys.CART_ID, STORED_CART_UUID));

        verify(mockCartService).removeItem(STORED_CART_UUID, TICKET_UUID);
    }

    @Test
    public void shouldClearCart_whenClear() throws Exception {
        when(mockCartService.view(STORED_CART_UUID)).thenReturn(mockCartDto);

        mockMvc.perform(post(CART_CLEAR_URL)
                .sessionAttr(SessionKeys.CART_ID, STORED_CART_UUID))
                .andExpect(status().isOk())
                .andExpect(request().sessionAttribute(SessionKeys.CART_ID, STORED_CART_UUID));

        verify(mockCartService).clear(STORED_CART_UUID);
    }
}
