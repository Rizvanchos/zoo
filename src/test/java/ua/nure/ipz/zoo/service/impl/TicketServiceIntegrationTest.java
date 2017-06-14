package ua.nure.ipz.zoo.service.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import ua.nure.ipz.zoo.dto.TicketDto;
import ua.nure.ipz.zoo.exception.NotTicketTypeException;
import ua.nure.ipz.zoo.exception.ServiceValidationException;
import ua.nure.ipz.zoo.service.TicketService;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ua.nure.ipz.zoo.config.ZooJUnitConfiguration.class)
@Transactional
public class TicketServiceIntegrationTest {

    private static final BigDecimal NEW_TICKET_PRICE = BigDecimal.TEN;
    private static final BigDecimal TICKET_PRICE = BigDecimal.ONE;
    private static final String UNKNOWN_TICKET_TYPE = "UNKNOWN";
    private static final String TICKET_TYPE = "STANDARD";

    @Autowired
    private TicketService ticketService;

    private UUID ticketId;

    @Before
    public void setUp() throws NotTicketTypeException {
        ticketId = ticketService.create(TICKET_TYPE, TICKET_PRICE);
    }

    @Test
    public void shouldCreateNewTicket_whenPassValidParameters() throws NotTicketTypeException {
        TicketDto ticketDto = ticketService.view(ticketId);

        assertEquals(TICKET_TYPE, ticketDto.getType());
        assertEquals(TICKET_PRICE, ticketDto.getPrice());
    }

    @Test
    public void shouldCreateNewTicket_whenPassValidTicketTypeInLowerCase() throws NotTicketTypeException {
        UUID ticketId = ticketService.create(TICKET_TYPE.toLowerCase(), TICKET_PRICE);
        assertNotNull(ticketId);
    }

    @Test(expected = NotTicketTypeException.class)
    public void shouldThrowException_whenPassNotTicketType() throws NotTicketTypeException {
        ticketService.create(UNKNOWN_TICKET_TYPE, TICKET_PRICE);
    }

    @Test(expected = ServiceValidationException.class)
    public void shouldThrowException_whenTicketPriceLessThenZero() throws NotTicketTypeException {
        ticketService.create(TICKET_TYPE, BigDecimal.ZERO);
    }

    @Test(expected = ServiceValidationException.class)
    public void shouldThrowException_whenChangeNullTicketPrice(){
        ticketService.changePrice(null, TICKET_PRICE);
    }

    @Test
    public void shouldChangeTicketPrice_whenCallChangePrice() throws NotTicketTypeException {
        ticketService.changePrice(ticketId, NEW_TICKET_PRICE);

        TicketDto ticketDto = ticketService.view(ticketId);
        assertEquals(NEW_TICKET_PRICE, ticketDto.getPrice());
    }
}
