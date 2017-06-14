package ua.nure.ipz.zoo.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
import ua.nure.ipz.zoo.controller.constant.ErrorKeys;
import ua.nure.ipz.zoo.controller.constant.SessionKeys;
import ua.nure.ipz.zoo.controller.constant.ViewNames;
import ua.nure.ipz.zoo.dto.OrderDto;
import ua.nure.ipz.zoo.service.OrderService;

@RunWith(MockitoJUnitRunner.class)
public class OrderControllerTest {

    private static final String EMPTY_STRING = "";
    private static final String NAME = "Name";
    private static final String PHONE = "Phone";
    private static final String EMAIL = "Email";
    private static final String STATUS = "Status";
    private static final String COMMENT = "Comment";
    private static final String ORDER_URL = "/order/";
    private static final UUID ORDER_UUID = UUID.randomUUID();
    private static final BigDecimal TOTAL_PRICE = BigDecimal.TEN;
    private static final String TIME_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static final LocalDateTime PLACEMENT_TIME = LocalDateTime.now();

    @Mock
    private OrderDto mockOrderDto;
    @Mock
    private OrderService mockOrderService;
    @InjectMocks
    private OrderController orderController;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/jsp/view/");
        viewResolver.setSuffix(".jsp");

        mockMvc = MockMvcBuilders.standaloneSetup(orderController).setViewResolvers(viewResolver).build();
    }

    @Test
    public void shouldReturnErrorMessage_whenOrderNotStoredInSession() throws Exception {
        mockMvc.perform(get(ORDER_URL)).andExpect(status().isOk()).andExpect(view().name(ViewNames.ERROR))
                .andExpect(model().attribute(ControllerConstants.ErrorView.TITLE, ErrorKeys.NO_ORDER_TITLE))
                .andExpect(model().attribute(ControllerConstants.ErrorView.MESSAGE, ErrorKeys.NO_ORDER_MESSAGE))
                .andExpect(model().attribute(ControllerConstants.ErrorView.ARGUMENTS, EMPTY_STRING));
    }

    @Test
    public void shouldReturnOrderView_whenOrderStoredInSession() throws Exception {
        when(mockOrderService.view(ORDER_UUID)).thenReturn(mockOrderDto);

        when(mockOrderDto.getDomainId()).thenReturn(ORDER_UUID);
        when(mockOrderDto.getTotalPrice()).thenReturn(TOTAL_PRICE);
        when(mockOrderDto.getOrderStatus()).thenReturn(STATUS);
        when(mockOrderDto.getCustomerName()).thenReturn(NAME);
        when(mockOrderDto.getCustomerPhone()).thenReturn(PHONE);
        when(mockOrderDto.getCustomerEmail()).thenReturn(EMAIL);
        when(mockOrderDto.getPlacementTime()).thenReturn(getFormattedTime(PLACEMENT_TIME));
        when(mockOrderDto.getComment()).thenReturn(COMMENT);

        mockMvc.perform(get(ORDER_URL)
                .sessionAttr(SessionKeys.ORDER_ID, ORDER_UUID))
                .andExpect(status().isOk())
                .andExpect(view().name(ViewNames.ORDER))
                .andExpect(model().attribute(ControllerConstants.OrderView.ORDER_ID, mockOrderDto.getDomainId().toString()))
                .andExpect(model().attribute(ControllerConstants.OrderView.TOTAL_PRICE, mockOrderDto.getTotalPrice()))
                .andExpect(model().attribute(ControllerConstants.OrderView.STATUS, mockOrderDto.getOrderStatus()))
                .andExpect(model().attribute(ControllerConstants.OrderView.NAME, mockOrderDto.getCustomerName()))
                .andExpect(model().attribute(ControllerConstants.OrderView.PHONE, mockOrderDto.getCustomerPhone()))
                .andExpect(model().attribute(ControllerConstants.OrderView.EMAIL, mockOrderDto.getCustomerEmail()))
                .andExpect(model().attribute(ControllerConstants.OrderView.PLACEMENT_TIME, mockOrderDto.getPlacementTime()))
                .andExpect(model().attribute(ControllerConstants.OrderView.COMMENT, mockOrderDto.getComment()));
    }

    private String getFormattedTime(LocalDateTime time) {
        return DateTimeFormatter.ofPattern(TIME_FORMAT_PATTERN).format(time);
    }
}
