package ua.nure.ipz.zoo.entity;

import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Map;

@Embeddable
public class Discount {
    private static final TicketType DEFAULT_TICKET_TYPE = TicketType.STANDARD;
    private static final int DEFAULT_BARRIER = 10;
    private static final float DEFAULT_COEFFICIENT = 0.7f;

    private TicketType type;
    private int barrier;
    private float coefficient;

    public Discount() {
        this.type = DEFAULT_TICKET_TYPE;
        this.barrier = DEFAULT_BARRIER;
        this.coefficient = DEFAULT_COEFFICIENT;
    }

    public Discount(TicketType type, int barrier, float coefficient) {
        this.type = type;
        this.barrier = barrier;
        this.coefficient = coefficient;
    }

    public BigDecimal discountPrice(Cart cart) {
        int amount = cart.getOrderedTickets().entrySet()
                .stream()
                .filter(e -> e.getKey().getType() == type)
                .mapToInt(Map.Entry::getValue)
                .sum();
        BigDecimal discounted = cart.getTotalPrice();
        if (amount >= barrier) {
            discounted = discounted.multiply(BigDecimal.valueOf(coefficient));
        }
        return discounted;
    }

    public TicketType getType() {
        return type;
    }

    public int getBarrier() {
        return barrier;
    }

    public float getCoefficient() {
        return coefficient;
    }

    public void setType(TicketType type) {
        this.type = type;
    }

    public void setBarrier(int barrier) {
        this.barrier = barrier;
    }

    public void setCoefficient(float coefficient) {
        this.coefficient = coefficient;
    }
}
