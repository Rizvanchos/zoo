package ua.nure.ipz.zoo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.nure.ipz.zoo.dto.CartDto;
import ua.nure.ipz.zoo.dto.TicketDto;
import ua.nure.ipz.zoo.dto.builder.DtoBuilder;
import ua.nure.ipz.zoo.entity.Cart;
import ua.nure.ipz.zoo.entity.Ticket;
import ua.nure.ipz.zoo.exception.LockingEmptyCartException;
import ua.nure.ipz.zoo.exception.RemovingUnexistingItemException;
import ua.nure.ipz.zoo.exception.UnmodifiableCartException;
import ua.nure.ipz.zoo.repository.CartRepository;
import ua.nure.ipz.zoo.repository.TicketRepository;
import ua.nure.ipz.zoo.service.CartService;
import ua.nure.ipz.zoo.service.ServiceUtils;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class DefaultCartService implements CartService {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private DtoBuilder<Cart, CartDto> cartDtoBuilder;

    @Override
    @Transactional
    public UUID create() {
        Cart cart = new Cart();
        cartRepository.save(cart);
        return cart.getDomainId();
    }

    @Override
    public Map<TicketDto, Integer> viewPurchases(UUID cartId) {
        return view(cartId).getItems();
    }

    @Override
    @Transactional
    public void addItem(UUID cartId, UUID ticketId, int quantity) throws UnmodifiableCartException {
        Cart cart = resolveCart(cartId);
        Ticket ticket = resolveTicket(ticketId);

        Map<Ticket, Integer> tickets = cart.getOrderedTickets();
        if(tickets.containsKey(ticket)){
            quantity += tickets.get(ticket);
        }

        tickets.put(ticket, quantity);
    }

    @Override
    @Transactional
    public void updateItem(UUID cartId, UUID ticketId, int quantity) throws UnmodifiableCartException {
        Cart cart = resolveCart(cartId);
        Ticket ticket = resolveTicket(ticketId);

        cart.getOrderedTickets().replace(ticket, quantity);
    }

    @Override
    @Transactional
    public void removeItem(UUID cartId, UUID ticketId) throws UnmodifiableCartException, RemovingUnexistingItemException {
        Cart cart = resolveCart(cartId);
        Ticket ticket = resolveTicket(ticketId);

        if (!cart.getOrderedTickets().containsKey(ticket)) {
            throw new RemovingUnexistingItemException(ticket.getName(), "cart", cartId);
        }

        cart.getOrderedTickets().remove(ticket);
    }

    @Override
    @Transactional
    public void lock(UUID cartId) throws LockingEmptyCartException {
        Cart cart = resolveCart(cartId);
        cart.checkout();
    }

    @Override
    @Transactional
    public void clear(UUID cartId) throws UnmodifiableCartException {
        Cart cart = resolveCart(cartId);
        cart.clear();
    }

    @Override
    public List<UUID> viewAll() {
        return cartRepository.selectAllDomainIds();
    }

    @Override
    public CartDto view(UUID domainId) {
        Cart cart = resolveCart(domainId);
        return cartDtoBuilder.toDto(cart);
    }

    private Cart resolveCart(UUID cartId) {
        return ServiceUtils.resolveEntity(cartRepository, cartId);
    }

    private Ticket resolveTicket(UUID ticketId) {
        return ServiceUtils.resolveEntity(ticketRepository, ticketId);
    }
}
