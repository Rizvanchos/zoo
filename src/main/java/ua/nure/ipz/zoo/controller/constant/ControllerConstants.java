package ua.nure.ipz.zoo.controller.constant;

public interface ControllerConstants {
    interface CartView {
        String CART = "cart";
    }

    interface CheckoutView {
        String CART = "cart";
    }

    interface MenuView {
        String TICKETS = "tickets";
        String ANIMALS = "animals";
        String AVIARIES = "aviaries";
    }

    interface OrderView {
        String ORDER_ID = "orderId";
        String TOTAL_PRICE = "totalPrice";
        String STATUS = "status";
        String EMAIL = "customerEmail";
        String NAME = "customerName";
        String PHONE = "customerPhone";
        String PLACEMENT_TIME = "placementTime";
        String COMMENT = "comment";
    }

    interface ErrorView {
        String TITLE = "errorTitle";
        String MESSAGE = "errorMessage";
        String ARGUMENTS = "errorArguments";
    }
}
