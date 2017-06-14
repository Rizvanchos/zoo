package ua.nure.ipz.zoo.entity;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import java.math.BigDecimal;

@Entity
public class Ticket extends Product {
    @Enumerated
    private TicketType type;

    public Ticket() {
    }

    public Ticket(TicketType type, BigDecimal price) {
        super(type.name(), price);
        this.type = type;
    }

    public TicketType getType() {
        return type;
    }

    public void setType(TicketType type) {
        this.type = type;
    }
}
