package ua.nure.ipz.zoo.dto;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class TicketDto extends DomainEntityDto<TicketDto> {
    private BigDecimal price;
    private String type;

    public TicketDto(UUID domainId, BigDecimal price, String type) {
        super(domainId);
        this.price = price;
        this.type = type;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getType() {
        return type;
    }

    @Override
    protected List<Object> getAttributesToIncludeInEqualityCheck() {
        return Arrays.asList(getDomainId(), getPrice(), getType());
    }

    @Override
    public String toString() {
        return String.format("ID = %s\nPrice = %s\nType = %s", getDomainId(), getPrice(), getType());
    }
}
