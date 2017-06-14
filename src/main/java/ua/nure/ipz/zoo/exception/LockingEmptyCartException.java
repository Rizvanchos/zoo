package ua.nure.ipz.zoo.exception;

import java.util.UUID;

public class LockingEmptyCartException extends DomainLogicException {
    public LockingEmptyCartException(UUID cartId) {
        super(String.format("Attempted to lock empty cart #%s", cartId));
    }
}
