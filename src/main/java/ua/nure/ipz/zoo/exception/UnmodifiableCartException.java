package ua.nure.ipz.zoo.exception;

import java.util.UUID;

public class UnmodifiableCartException extends DomainLogicException {
    public UnmodifiableCartException(UUID cartId) {
        super(String.format("Attempted to modify a locked cart #%s", cartId));
    }
}