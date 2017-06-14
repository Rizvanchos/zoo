package ua.nure.ipz.zoo.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class CartDto extends DomainEntityDto<CartDto> {
    private Map<TicketDto, Integer> items;
    private BigDecimal totalCost;
    private boolean modifiable;

    public CartDto(UUID domainId, Map<TicketDto, Integer> items, BigDecimal totalCost, boolean modifiable) {
        super(domainId);
        this.items = items;
        this.totalCost = totalCost;
        this.modifiable = modifiable;
    }

    public Map<TicketDto, Integer> getItems() {
        return items;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public boolean isModifiable() {
        return modifiable;
    }

    @Override
    protected List<Object> getAttributesToIncludeInEqualityCheck() {
        List<Object> attributes = new ArrayList<>();
        attributes.add(getDomainId());
        items.entrySet().forEach(entry -> {
            attributes.add(entry.getKey());
            attributes.add(entry.getValue());
        });
        attributes.add(getTotalCost());
        attributes.add(isModifiable());
        return attributes;
    }

    @Override
    public String toString() {
        String tickets = items.entrySet().stream()
                .map(entry -> "\tTicket type: " + entry.getKey().getType() + ", price: " + entry.getKey().getPrice() + ", quantity: " + entry.getValue() + "\n")
                .reduce(String::concat).get().trim();
        return String.format("ID = %s\nOrderedTickets: \n\t%s", getDomainId(), tickets);
    }
}
