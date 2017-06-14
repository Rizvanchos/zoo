package ua.nure.ipz.zoo.repository.jpa;

import org.springframework.stereotype.Repository;
import ua.nure.ipz.zoo.entity.Ticket;
import ua.nure.ipz.zoo.repository.TicketRepository;

@Repository
public class DefaultTicketRepository extends BasicRepository<Ticket> implements TicketRepository {
    public DefaultTicketRepository() {
        super(Ticket.class);
    }
}
