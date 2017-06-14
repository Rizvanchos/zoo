package ua.nure.ipz.zoo.controller;

import ua.nure.ipz.zoo.controller.constant.SessionKeys;
import ua.nure.ipz.zoo.dto.CartDto;
import ua.nure.ipz.zoo.dto.OrderDto;
import ua.nure.ipz.zoo.service.CartService;
import ua.nure.ipz.zoo.service.OrderService;

import javax.servlet.http.HttpSession;
import java.util.UUID;

public final class SessionDataProvider {

    public static CartDto currentCart(CartService cartService, HttpSession session) {
        UUID cartId = currentCartId(cartService, session);
        return cartService.view(cartId);
    }

    public static UUID currentCartId(CartService cartService, HttpSession session) {
        UUID cartId = (UUID) session.getAttribute(SessionKeys.CART_ID);
        if (cartId == null) {
            cartId = cartService.create();
            session.setAttribute(SessionKeys.CART_ID, cartId);
        }
        return cartId;
    }

    public static void resetCurrentCartId(HttpSession session) {
        session.removeAttribute(SessionKeys.CART_ID);
    }

    public static OrderDto currentOrder(OrderService orderService, HttpSession session) {
        UUID orderId = currentOrderId(session);
        return orderId != null ? orderService.view(orderId) : null;
    }

    public static UUID currentOrderId(HttpSession session) {
        return (UUID) session.getAttribute(SessionKeys.ORDER_ID);
    }

    public static void setCurrentOrderId(HttpSession session, UUID orderId) {
        session.setAttribute(SessionKeys.ORDER_ID, orderId);
    }
}
