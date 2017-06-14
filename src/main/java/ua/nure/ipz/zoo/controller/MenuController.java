/* (C) 2014-2016, Sergei Zaychenko, NURE, Kharkiv, Ukraine */

package ua.nure.ipz.zoo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ua.nure.ipz.zoo.controller.constant.ControllerConstants;
import ua.nure.ipz.zoo.controller.constant.ViewNames;
import ua.nure.ipz.zoo.dto.AnimalDto;
import ua.nure.ipz.zoo.dto.AviaryDto;
import ua.nure.ipz.zoo.dto.TicketDto;
import ua.nure.ipz.zoo.service.AnimalService;
import ua.nure.ipz.zoo.service.AviaryService;
import ua.nure.ipz.zoo.service.TicketService;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/menu")
public class MenuController {
    @Resource
    private AnimalService animalService;
    @Resource
    private AviaryService aviaryService;
    @Resource
    private TicketService ticketService;

    @RequestMapping(method = RequestMethod.GET)
    public String handleRoot(Model model) {
        model.addAttribute(ControllerConstants.MenuView.ANIMALS, getAnimals());
        model.addAttribute(ControllerConstants.MenuView.AVIARIES, getAviaries());
        model.addAttribute(ControllerConstants.MenuView.TICKETS, getTickets());

        return ViewNames.MENU;
    }

    private List<AnimalDto> getAnimals() {
        return animalService.viewAll().stream().map(animalService::view).sorted(Comparator.comparing(AnimalDto::getName)).collect(Collectors.toList());
    }

    private List<AviaryDto> getAviaries() {
        return aviaryService.viewAll().stream().map(aviaryService::view).filter(AviaryDto::isContactFlag).sorted(Comparator.comparingInt(AviaryDto::getNumber)).collect(Collectors.toList());
    }

    private List<TicketDto> getTickets() {
        return ticketService.viewAll().stream().map(ticketService::view).sorted(Comparator.comparing(TicketDto::getPrice)).collect(Collectors.toList());
    }
}
