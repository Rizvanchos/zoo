package ua.nure.ipz.zoo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.nure.ipz.zoo.dto.OrderDto;
import ua.nure.ipz.zoo.dto.builder.DtoBuilder;
import ua.nure.ipz.zoo.entity.Cart;
import ua.nure.ipz.zoo.entity.Contact;
import ua.nure.ipz.zoo.entity.Discount;
import ua.nure.ipz.zoo.entity.OperatorAccount;
import ua.nure.ipz.zoo.entity.Order;
import ua.nure.ipz.zoo.entity.PaymentInfo;
import ua.nure.ipz.zoo.entity.TicketType;
import ua.nure.ipz.zoo.exception.ModifiableCartException;
import ua.nure.ipz.zoo.exception.OrderLifecycleException;
import ua.nure.ipz.zoo.exception.ServiceUnresolvedEntityException;
import ua.nure.ipz.zoo.repository.AccountRepository;
import ua.nure.ipz.zoo.repository.CartRepository;
import ua.nure.ipz.zoo.repository.OrderRepository;
import ua.nure.ipz.zoo.service.OrderService;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static ua.nure.ipz.zoo.service.ServiceUtils.resolveEntity;

@Service
public class DefaultOrderService implements OrderService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private DtoBuilder<Order, OrderDto> orderDtoBuilder;

    @Override
    @Transactional
    public UUID create(String name, String email, String contactPhone, String comment, UUID cartId) throws ModifiableCartException {
        Cart cart = resolveCart(cartId);
        Order order = new Order(cart, new Contact(name, email, contactPhone), LocalDateTime.now());
        order.setComment(comment);

        orderRepository.save(order);
        return order.getDomainId();
    }

    @Override
    @Transactional
    public void setDiscount(UUID orderId, String ticketType, int barrier, float coefficient) {
        Order order = resolveOrder(orderId);
        order.setDiscount(new Discount(TicketType.valueOf(ticketType), barrier, coefficient));
    }

    @Override
    @Transactional
    public void setPaymentInfo(UUID orderId, String cardType, String cardNumber, String cardCvv) {
        Order order = resolveOrder(orderId);
        order.setPaymentInfo(new PaymentInfo(cardType, cardNumber, cardCvv));
    }

    @Override
    public OrderDto findOrder(@NotNull UUID orderId) {
        Order order = orderRepository.findByDomainId(orderId);
        return order != null ? orderDtoBuilder.toDto(order) : null;
    }

    @Override
    public void process(@NotNull UUID orderId, @NotNull UUID operatorId) throws OrderLifecycleException {
        Order order = resolveOrder(orderId);
        OperatorAccount operatorAccount = resolveOperator(operatorId);

        order.process();
        operatorAccount.trackOrder(order);
    }

    @Override
    public void cancel(@NotNull UUID orderId) throws OrderLifecycleException {
        Order order = resolveOrder(orderId);
        order.cancel();
    }

    @Override
    public List<UUID> viewAll() {
        return orderRepository.selectAllDomainIds();
    }

    @Override
    public OrderDto view(UUID domainId) {
        Order order = resolveOrder(domainId);
        return orderDtoBuilder.toDto(order);
    }

    private Order resolveOrder(UUID orderId) {
        return resolveEntity(orderRepository, orderId);
    }

    private Cart resolveCart(UUID cartId) {
        return resolveEntity(cartRepository, cartId);
    }

    private OperatorAccount resolveOperator(UUID operatorId) {
        OperatorAccount account = accountRepository.findOperatorByDomainId(operatorId);
        if (account == null) {
            throw new ServiceUnresolvedEntityException(OperatorAccount.class, operatorId);
        }

        return account;
    }
}
