package ua.nure.ipz.zoo.dto.builder;

import org.springframework.stereotype.Component;
import ua.nure.ipz.zoo.dto.TicketDto;
import ua.nure.ipz.zoo.entity.Ticket;

@Component
public class TicketDtoBuilder implements DtoBuilder<Ticket, TicketDto> {
    @Override
    public TicketDto toDto(Ticket source) {
        return new TicketDto(source.getDomainId(), source.getPrice(), source.getType().toString());
    }
}
