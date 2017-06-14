package ua.nure.ipz.zoo.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.math.BigDecimal;

import org.junit.Test;

public class TicketTest {

    private static final BigDecimal TICKET_PRICE = BigDecimal.TEN;
    private static final TicketType TICKET_TYPE = TicketType.STANDARD;

    @Test
    public void shouldCreateTicketWithDefaultValues_whenCallDefaultConstructor(){
        Ticket ticket = new Ticket();
        assertNull(ticket.getType());
        assertNull(ticket.getPrice());
    }

    @Test
    public void shouldCreateTicketWithPassedParameters_whenCreateTicket() {
        Ticket ticket = new Ticket(TICKET_TYPE, TICKET_PRICE);

        assertEquals(TICKET_TYPE, ticket.getType());
        assertEquals(TICKET_PRICE, ticket.getPrice());
    }

    @Test
    public void shouldSetNewTicketType_whenSetNewType(){
        Ticket ticket = new Ticket();
        ticket.setType(TICKET_TYPE);

        assertEquals(TICKET_TYPE, ticket.getType());
    }
}
