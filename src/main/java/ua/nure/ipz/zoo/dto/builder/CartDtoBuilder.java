package ua.nure.ipz.zoo.dto.builder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.nure.ipz.zoo.dto.CartDto;
import ua.nure.ipz.zoo.dto.TicketDto;
import ua.nure.ipz.zoo.entity.Cart;
import ua.nure.ipz.zoo.entity.Ticket;

import java.util.HashMap;
import java.util.Map;

@Component
public class CartDtoBuilder implements DtoBuilder<Cart, CartDto> {
    @Autowired
    private DtoBuilder<Ticket, TicketDto> ticketDtoBuilder;

    @Override
    public CartDto toDto(Cart source) {
        return new CartDto(source.getDomainId(), getTicketsDto(source.getOrderedTickets()), source.getTotalPrice(), source.isModifiable());
    }

    private Map<TicketDto, Integer> getTicketsDto(Map<Ticket, Integer> tickets) {
        Map<TicketDto, Integer> ticketsDto = new HashMap<>();
        tickets.entrySet().forEach(ticket -> ticketsDto.put(ticketDtoBuilder.toDto(ticket.getKey()), ticket.getValue()));
        return ticketsDto;
    }
}
