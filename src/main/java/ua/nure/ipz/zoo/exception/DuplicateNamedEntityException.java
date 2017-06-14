package ua.nure.ipz.zoo.exception;

public class DuplicateNamedEntityException extends DomainLogicException {
    public DuplicateNamedEntityException(Class c, String name) {
        super(String.format("Duplicate %s object called \"%s\"", c.getName(), name));
    }
}