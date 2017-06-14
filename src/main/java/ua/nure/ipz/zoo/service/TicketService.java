package ua.nure.ipz.zoo.service;

import org.hibernate.validator.constraints.NotBlank;
import ua.nure.ipz.zoo.dto.TicketDto;
import ua.nure.ipz.zoo.exception.NotTicketTypeException;
import ua.nure.ipz.zoo.service.validation.Price;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

public interface TicketService extends Service<TicketDto> {
    UUID create(@NotBlank String type, @Price BigDecimal price) throws NotTicketTypeException;

    void changePrice(@NotNull UUID ticketId, @Price BigDecimal newPrice);
}
