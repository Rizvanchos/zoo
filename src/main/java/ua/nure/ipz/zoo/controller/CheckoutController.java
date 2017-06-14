package ua.nure.ipz.zoo.controller;

import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import ua.nure.ipz.zoo.controller.constant.ControllerConstants;
import ua.nure.ipz.zoo.controller.constant.ViewNames;
import ua.nure.ipz.zoo.dto.CartDto;
import ua.nure.ipz.zoo.exception.DomainLogicException;
import ua.nure.ipz.zoo.service.CartService;
import ua.nure.ipz.zoo.service.OrderService;

@Controller
@RequestMapping("/checkout")
public class CheckoutController {
    @Resource
    private OrderService orderService;
    @Resource
    private CartService cartService;

    @RequestMapping(method = RequestMethod.GET)
    public String startCheckout(HttpSession session, Map<String, Object> model) {
        CartDto cartDto = SessionDataProvider.currentCart(cartService, session);
        model.put(ControllerConstants.CheckoutView.CART, cartDto);
        return ViewNames.CHECKOUT;
    }

    @RequestMapping(method = RequestMethod.POST)
    public View submitCheckout(HttpSession session, @RequestParam String contactName, @RequestParam String contactEmail,
                               @RequestParam String contactPhone, @RequestParam String comment) throws DomainLogicException {
        CartDto cartDto = SessionDataProvider.currentCart(cartService, session);
        if (cartDto.getItems().isEmpty()) {
            return new RedirectView("/checkout/");
        }

        cartService.lock(cartDto.getDomainId());
        UUID orderId = orderService.create(contactName, contactEmail, contactPhone, comment, cartDto.getDomainId());

        SessionDataProvider.setCurrentOrderId(session, orderId);
        SessionDataProvider.resetCurrentCartId(session);

        return new RedirectView("/order/");
    }
}