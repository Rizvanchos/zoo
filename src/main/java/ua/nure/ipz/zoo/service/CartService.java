package ua.nure.ipz.zoo.service;

import ua.nure.ipz.zoo.dto.CartDto;
import ua.nure.ipz.zoo.dto.TicketDto;
import ua.nure.ipz.zoo.exception.LockingEmptyCartException;
import ua.nure.ipz.zoo.exception.RemovingUnexistingItemException;
import ua.nure.ipz.zoo.exception.UnmodifiableCartException;
import ua.nure.ipz.zoo.service.validation.Quantity;

import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.UUID;

public interface CartService extends Service<CartDto> {
    UUID create();

    Map<TicketDto, Integer> viewPurchases(@NotNull UUID cartId);

    void addItem(@NotNull UUID cartId, @NotNull UUID ticketId, @Quantity int quantity) throws UnmodifiableCartException;

    void updateItem(@NotNull UUID cartId, @NotNull UUID ticketId, @Quantity int quantity) throws UnmodifiableCartException;

    void removeItem(@NotNull UUID cartId, @NotNull UUID ticketId) throws UnmodifiableCartException, RemovingUnexistingItemException;

    void lock(@NotNull UUID cartId) throws LockingEmptyCartException;

    void clear(@NotNull UUID cartId) throws UnmodifiableCartException;
}
