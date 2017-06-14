package ua.nure.ipz.zoo.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Collections;
import java.util.List;
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
import ua.nure.ipz.zoo.controller.constant.ViewNames;
import ua.nure.ipz.zoo.dto.AnimalDto;
import ua.nure.ipz.zoo.dto.AviaryDto;
import ua.nure.ipz.zoo.dto.TicketDto;
import ua.nure.ipz.zoo.service.AnimalService;
import ua.nure.ipz.zoo.service.AviaryService;
import ua.nure.ipz.zoo.service.TicketService;

@RunWith(MockitoJUnitRunner.class)
public class MenuControllerTest {

    private static final UUID ANIMAL_UUID = UUID.randomUUID();
    private static final UUID AVIARY_UUID = UUID.randomUUID();
    private static final UUID TICKET_UUID = UUID.randomUUID();

    private static final String MENU_URL = "/menu/";

    @Mock
    private AnimalDto mockAnimalDto;
    @Mock
    private AviaryDto mockAviaryDto;
    @Mock
    private TicketDto mockTicketDto;
    @Mock
    private AnimalService mockAnimalService;
    @Mock
    private AviaryService mockAviaryService;
    @Mock
    private TicketService mockTicketService;

    @InjectMocks
    private MenuController menuController;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/jsp/view/");
        viewResolver.setSuffix(".jsp");

        mockMvc = MockMvcBuilders.standaloneSetup(menuController).setViewResolvers(viewResolver).build();
    }

    @Test
    public void shouldReturnMenuInformation_whenGoToMenu() throws Exception {
        when(mockAviaryDto.isContactFlag()).thenReturn(Boolean.TRUE);

        when(mockAnimalService.viewAll()).thenReturn(getListFromUUID(ANIMAL_UUID));
        when(mockAviaryService.viewAll()).thenReturn(getListFromUUID(AVIARY_UUID));
        when(mockTicketService.viewAll()).thenReturn(getListFromUUID(TICKET_UUID));
        when(mockAnimalService.view(ANIMAL_UUID)).thenReturn(mockAnimalDto);
        when(mockAviaryService.view(AVIARY_UUID)).thenReturn(mockAviaryDto);
        when(mockTicketService.view(TICKET_UUID)).thenReturn(mockTicketDto);

        mockMvc.perform(get(MENU_URL))
                .andExpect(status().isOk())
                .andExpect(view().name(ViewNames.MENU))
                .andExpect(model().attribute(ControllerConstants.MenuView.ANIMALS, Collections.singletonList(mockAnimalDto)))
                .andExpect(model().attribute(ControllerConstants.MenuView.AVIARIES, Collections.singletonList(mockAviaryDto)))
                .andExpect(model().attribute(ControllerConstants.MenuView.TICKETS, Collections.singletonList(mockTicketDto)));
    }

    private List<UUID> getListFromUUID(UUID uuid) {
        return Collections.singletonList(uuid);
    }
}
