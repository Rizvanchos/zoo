package ua.nure.ipz.zoo.exception;

import java.util.UUID;

public class RemovingUnexistingItemException extends DomainLogicException {
    public RemovingUnexistingItemException(String itemName, String entityName, UUID domainId) {
        super(String.format("Removing item \"%s\" that does not exist in %s #%s", itemName, entityName, domainId));
    }
}
