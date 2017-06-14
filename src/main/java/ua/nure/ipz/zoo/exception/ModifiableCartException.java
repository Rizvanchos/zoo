package ua.nure.ipz.zoo.exception;

import java.util.UUID;

public class ModifiableCartException extends DomainLogicException {
    public ModifiableCartException(UUID cartId) {
        super(String.format("Cart #%s must be locked by this moment", cartId));
    }
}