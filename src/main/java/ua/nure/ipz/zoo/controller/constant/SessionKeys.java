package ua.nure.ipz.zoo.controller.constant;

import org.springframework.web.servlet.i18n.SessionLocaleResolver;

public interface SessionKeys {
    String ORDER_ID = "ORDER_ID";
    String CART_ID = "CART_ID";
    String LOCALE = SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME;
}
