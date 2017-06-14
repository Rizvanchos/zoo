package ua.nure.ipz.zoo.exception;

public class NotTicketTypeException extends DomainLogicException {
    public NotTicketTypeException(String ticketType) {
        super(String.format("Not a ticket type: " + ticketType));
    }
}
