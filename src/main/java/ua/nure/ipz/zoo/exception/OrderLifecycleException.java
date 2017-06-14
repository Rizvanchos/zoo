package ua.nure.ipz.zoo.exception;

import java.util.UUID;

public class OrderLifecycleException extends DomainLogicException {
    public OrderLifecycleException(UUID orderId, String currentStatus, String expectedStatus) {
        super(String.format("Order #%s must have %s status, while %s is a current status", orderId, expectedStatus, currentStatus));
    }
}
