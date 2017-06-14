package ua.nure.ipz.zoo.exception;

import java.util.Collection;

public class DuplicationEntityException extends DomainLogicException {
    public DuplicationEntityException(Collection<?> collection, String duplicatedObject) {
        super(String.format("%s already contains %s", collection, duplicatedObject));
    }
}
