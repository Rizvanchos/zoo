package ua.nure.ipz.zoo.exception;

import java.util.UUID;

public class ServiceUnresolvedEntityException extends ServiceValidationException {
    public ServiceUnresolvedEntityException(Class entityType, UUID entityId) {
        super(String.format("Unresolved entity #%s of type %s", entityId, entityType.getName()));
    }
}