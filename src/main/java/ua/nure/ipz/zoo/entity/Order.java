package ua.nure.ipz.zoo.entity;

import ua.nure.ipz.zoo.exception.ModifiableCartException;
import ua.nure.ipz.zoo.exception.OrderLifecycleException;
import ua.nure.ipz.zoo.util.DomainEntity;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "Orders")
public class Order extends DomainEntity {
    @OneToOne
    private Cart cart;
    @Enumerated
    private OrderStatus status;
    @Embedded
    private Contact contact;
    @Embedded
    private PaymentInfo paymentInfo;
    @Embedded
    @Transient
    private Discount discount;
    private String comment;
    private BigDecimal basicPrice;
    private LocalDateTime placementTime;

    public Order() {
    }

    public Order(Cart cart, Contact contact, LocalDateTime placementTime) throws ModifiableCartException {
        if (cart.isModifiable()) {
            throw new ModifiableCartException(cart.getDomainId());
        }

        this.cart = cart;
        this.basicPrice = cart.getTotalPrice();
        this.contact = contact;
        this.status = OrderStatus.ACCEPTED;
        this.placementTime = placementTime;
    }

    public BigDecimal getTotalPrice() {
        return discount != null ? discount.discountPrice(cart) : basicPrice;
    }

    public void process() throws OrderLifecycleException {
        if (status != OrderStatus.ACCEPTED) {
            throw new OrderLifecycleException(getDomainId(), status.toString(), OrderStatus.ACCEPTED.toString());
        }
        status = OrderStatus.PROCESSING;
    }

    public void finish() throws OrderLifecycleException {
        if (status != OrderStatus.PROCESSING) {
            throw new OrderLifecycleException(getDomainId(), status.toString(), OrderStatus.PROCESSING.toString());
        }
        status = OrderStatus.FINISHED;
    }

    public void cancel() {
        status = OrderStatus.CANCELLED;
    }

    public Cart getCart() {
        return cart;
    }

    public Contact getContact() {
        return contact;
    }

    public PaymentInfo getPaymentInfo() {
        return paymentInfo;
    }

    public void setPaymentInfo(PaymentInfo paymentInfo) {
        this.paymentInfo = paymentInfo;
    }

    public BigDecimal getBasicPrice() {
        return basicPrice;
    }

    public Discount getDiscount() {
        return discount;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setPlacementTime(LocalDateTime placementTime) {
        this.placementTime = placementTime;
    }

    public LocalDateTime getPlacementTime() {
        return placementTime;
    }
}
