package ua.nure.ipz.zoo.exception;

import java.util.UUID;

public class NotContactAviaryException extends DomainLogicException {
    public NotContactAviaryException(UUID aviaryId) {
        super(String.format("Aviary #%s isn't contact", aviaryId));
    }
}
