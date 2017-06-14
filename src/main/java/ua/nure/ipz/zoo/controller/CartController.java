package ua.nure.ipz.zoo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ua.nure.ipz.zoo.controller.constant.ControllerConstants;
import ua.nure.ipz.zoo.controller.constant.ViewNames;
import ua.nure.ipz.zoo.exception.DomainLogicException;
import ua.nure.ipz.zoo.service.CartService;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.UUID;

@Controller
@RequestMapping("/cart")
public class CartController {
    private static final String CART_ITEMS_ATTRIBUTE = "items";

    @Resource
    private CartService cartService;

    @RequestMapping(method = RequestMethod.GET)
    public String viewCart(HttpSession session, Model model) {
        model.addAttribute(ControllerConstants.CartView.CART, SessionDataProvider.currentCart(cartService, session));
        model.addAttribute(CART_ITEMS_ATTRIBUTE, cartService.viewPurchases(SessionDataProvider.currentCartId(cartService, session)));
        return ViewNames.CART;
    }

    @RequestMapping(value = "/additem", method = RequestMethod.POST)
    public ResponseEntity addItem(HttpSession session, @RequestParam UUID ticketId, @RequestParam int quantity) throws DomainLogicException {
        UUID cartId = SessionDataProvider.currentCartId(cartService, session);
        cartService.addItem(cartId, ticketId, quantity);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/setitem", method = RequestMethod.POST)
    public ResponseEntity setItem(HttpSession session, @RequestParam UUID ticketId, @RequestParam int newQuantity) throws DomainLogicException {
        UUID cartId = SessionDataProvider.currentCartId(cartService, session);
        cartService.updateItem(cartId, ticketId, newQuantity);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/removeitem", method = RequestMethod.POST)
    public ResponseEntity removeItem(HttpSession session, @RequestParam UUID ticketId) throws DomainLogicException {
        UUID cartId = SessionDataProvider.currentCartId(cartService, session);
        cartService.removeItem(cartId, ticketId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/clear", method = RequestMethod.POST)
    public ResponseEntity clear(HttpSession session) throws DomainLogicException {
        UUID cartId = SessionDataProvider.currentCartId(cartService, session);
        cartService.clear(cartId);
        return new ResponseEntity(HttpStatus.OK);
    }
}
