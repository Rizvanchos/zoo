package ua.nure.ipz.zoo.dto;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class OrderDto extends DomainEntityDto<OrderDto> {
    private String orderStatus;
    private String customerName;
    private String customerEmail;
    private String customerPhone;
    private BigDecimal basicPrice;
    private BigDecimal totalPrice;
    private String placementTime;
    private String comment;

    public OrderDto(UUID domainId, String orderStatus, String customerName, String customerEmail, String customerPhone,
                    BigDecimal basicPrice, BigDecimal totalPrice, String placementTime, String comment) {
        super(domainId);
        this.orderStatus = orderStatus;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.customerPhone = customerPhone;
        this.basicPrice = basicPrice;
        this.totalPrice = totalPrice;
        this.placementTime = placementTime;
        this.comment = comment;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public BigDecimal getBasicPrice() {
        return basicPrice;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public String getPlacementTime() {
        return placementTime;
    }

    public String getComment() {
        return comment;
    }

    @Override
    protected List<Object> getAttributesToIncludeInEqualityCheck() {
        return Arrays.asList(getDomainId(), getOrderStatus(), getCustomerName(), getCustomerEmail(), getCustomerPhone(),
                getBasicPrice(), getTotalPrice(), getPlacementTime(), getComment());
    }

    @Override
    public String toString() {
        return String.format("ID = %s\nOrderStatus = %s\nCustomerEmail = %s\nBasicPrice = %s\nTotalPrice = %s",
                getDomainId(), getOrderStatus(), getCustomerEmail(), getBasicPrice(), getTotalPrice());
    }
}
