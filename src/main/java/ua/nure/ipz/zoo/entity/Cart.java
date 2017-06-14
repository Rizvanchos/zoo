package ua.nure.ipz.zoo.entity;

import ua.nure.ipz.zoo.exception.LockingEmptyCartException;
import ua.nure.ipz.zoo.exception.UnmodifiableCartException;
import ua.nure.ipz.zoo.util.DomainEntity;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Entity
public class Cart extends DomainEntity {
    @ElementCollection
    private Map<Ticket, Integer> orderedTickets = new HashMap<>();

    private boolean modifiable = true;

    public Cart() {
    }

    public BigDecimal getTotalPrice() {
        return orderedTickets.entrySet().stream().map(f -> f.getKey().getPrice().multiply(BigDecimal.valueOf(f.getValue()))).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void checkout() throws LockingEmptyCartException {
        if (orderedTickets.isEmpty()) {
            throw new LockingEmptyCartException(getDomainId());
        }
        modifiable = false;
    }

    public void clear() throws UnmodifiableCartException {
        if (!isModifiable()) {
            throw new UnmodifiableCartException(getDomainId());
        }
        orderedTickets = new HashMap<>();
    }

    public Map<Ticket, Integer> getOrderedTickets() {
        return orderedTickets;
    }

    public void setOrderedTickets(Map<Ticket, Integer> orderedTickets) {
        this.orderedTickets = orderedTickets;
    }

    public boolean isModifiable() {
        return modifiable;
    }

    public void setModifiable(boolean modifiable) {
        this.modifiable = modifiable;
    }
}
