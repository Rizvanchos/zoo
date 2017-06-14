package ua.nure.ipz.zoo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.nure.ipz.zoo.dto.TicketDto;
import ua.nure.ipz.zoo.dto.builder.DtoBuilder;
import ua.nure.ipz.zoo.entity.Ticket;
import ua.nure.ipz.zoo.entity.TicketType;
import ua.nure.ipz.zoo.exception.NotTicketTypeException;
import ua.nure.ipz.zoo.repository.TicketRepository;
import ua.nure.ipz.zoo.service.ServiceUtils;
import ua.nure.ipz.zoo.service.TicketService;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class DefaultTicketService implements TicketService {
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private DtoBuilder<Ticket, TicketDto> ticketDtoBuilder;

    @Override
    @Transactional
    public UUID create(String type, BigDecimal price) throws NotTicketTypeException {
        String ticketType = type.toUpperCase();

        if (!isTicketType(ticketType)) {
            throw new NotTicketTypeException(type);
        }

        Ticket ticket = new Ticket(TicketType.valueOf(ticketType), price);
        ticketRepository.save(ticket);
        return ticket.getDomainId();
    }

    private boolean isTicketType(String ticketType) {
        return Arrays.asList(TicketType.values()).stream().anyMatch(ticket -> ticket.name().equals(ticketType));
    }

    @Override
    @Transactional
    public void changePrice(UUID ticketId, BigDecimal newPrice) {
        Ticket ticket = resolveTicket(ticketId);
        ticket.setPrice(newPrice);
    }

    @Override
    public List<UUID> viewAll() {
        return ticketRepository.selectAllDomainIds();
    }

    @Override
    public TicketDto view(UUID domainId) {
        Ticket ticket = resolveTicket(domainId);
        return ticketDtoBuilder.toDto(ticket);
    }

    private Ticket resolveTicket(UUID domainId) {
        return ServiceUtils.resolveEntity(ticketRepository, domainId);
    }
}
