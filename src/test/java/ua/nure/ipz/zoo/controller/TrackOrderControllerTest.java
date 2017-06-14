package ua.nure.ipz.zoo.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import ua.nure.ipz.zoo.controller.constant.ControllerConstants;
import ua.nure.ipz.zoo.controller.constant.ErrorKeys;
import ua.nure.ipz.zoo.controller.constant.SessionKeys;
import ua.nure.ipz.zoo.controller.constant.ViewNames;
import ua.nure.ipz.zoo.dto.OrderDto;
import ua.nure.ipz.zoo.service.OrderService;

@RunWith(MockitoJUnitRunner.class)
public class TrackOrderControllerTest {

    private static final UUID ORDER_UUID = UUID.randomUUID();

    private static final String ORDER_URL = "/order/";
    private static final String TRACK_ORDER_URL = "/trackorder/";
    private static final String RIGHT_EMAIL = "RightEmail";
    private static final String WRONG_EMAIL = "WrongEmail";
    private static final String INPUT_TRACK_ORDER_ID_ATTRIBUTE = "inputTrackOrderId";
    private static final String INPUT_TRACK_EMAIL_ID_ATTRIBUTE = "inputTrackOrderEmail";

    @Mock
    private OrderDto mockOrderDto;
    @Mock
    private OrderService mockOrderService;
    @InjectMocks
    private TrackOrderController trackOrderController;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(trackOrderController).build();
        when(mockOrderDto.getCustomerEmail()).thenReturn(RIGHT_EMAIL);
    }

    @Test
    public void shouldReturnError_whenNoOrderIdSpecified() throws Exception {
        mockMvc.perform(post(TRACK_ORDER_URL).param(INPUT_TRACK_EMAIL_ID_ATTRIBUTE, RIGHT_EMAIL)).andExpect(status().is4xxClientError());
    }

    @Test
    public void shouldReturnError_whenNoEmailIdSpecified() throws Exception {
        mockMvc.perform(post(TRACK_ORDER_URL).param(INPUT_TRACK_ORDER_ID_ATTRIBUTE, ORDER_UUID.toString())).andExpect(status().is4xxClientError());
    }

    @Test
    public void shouldReturnError_whenNoOrderExists() throws Exception {
        when(mockOrderService.findOrder(ORDER_UUID)).thenReturn(null);

        mockMvc.perform(post(TRACK_ORDER_URL)
                .param(INPUT_TRACK_EMAIL_ID_ATTRIBUTE, RIGHT_EMAIL)
                .param(INPUT_TRACK_ORDER_ID_ATTRIBUTE, ORDER_UUID.toString()))
                .andExpect(status().isOk())
                .andExpect(view().name(ViewNames.ERROR))
                .andExpect(model().attribute(ControllerConstants.ErrorView.TITLE, ErrorKeys.ORDER_NOT_FOUND_TITLE))
                .andExpect(model().attribute(ControllerConstants.ErrorView.MESSAGE, ErrorKeys.ORDER_NOT_FOUND_MESSAGE))
                .andExpect(model().attribute(ControllerConstants.ErrorView.ARGUMENTS, ORDER_UUID.toString()));
    }

    @Test
    public void shouldReturnError_whenOrderEmailNotMatches() throws Exception {
        when(mockOrderService.findOrder(ORDER_UUID)).thenReturn(mockOrderDto);

        mockMvc.perform(post(TRACK_ORDER_URL)
                .param(INPUT_TRACK_EMAIL_ID_ATTRIBUTE, WRONG_EMAIL)
                .param(INPUT_TRACK_ORDER_ID_ATTRIBUTE, ORDER_UUID.toString()))
                .andExpect(status().isOk())
                .andExpect(view().name(ViewNames.ERROR))
                .andExpect(model().attribute(ControllerConstants.ErrorView.TITLE, ErrorKeys.ORDER_ACCESS_VIOLATION_TITLE))
                .andExpect(model().attribute(ControllerConstants.ErrorView.MESSAGE, ErrorKeys.ORDER_ACCESS_VIOLATION_MESSAGE))
                .andExpect(model().attribute(ControllerConstants.ErrorView.ARGUMENTS, WRONG_EMAIL));
    }

    @Test
    public void shouldRedirect_whenOrderFoundEmailMatched() throws Exception {
        when(mockOrderService.findOrder(ORDER_UUID)).thenReturn(mockOrderDto);

        mockMvc.perform(post(TRACK_ORDER_URL)
                .param(INPUT_TRACK_EMAIL_ID_ATTRIBUTE, RIGHT_EMAIL)
                .param(INPUT_TRACK_ORDER_ID_ATTRIBUTE, ORDER_UUID.toString()))
                .andExpect(redirectedUrl(ORDER_URL))
                .andExpect(request().sessionAttribute(SessionKeys.ORDER_ID, ORDER_UUID));
    }
}
