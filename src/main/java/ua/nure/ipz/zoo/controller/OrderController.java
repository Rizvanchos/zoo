package ua.nure.ipz.zoo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ua.nure.ipz.zoo.controller.constant.ControllerConstants;
import ua.nure.ipz.zoo.controller.constant.ErrorKeys;
import ua.nure.ipz.zoo.controller.constant.ViewNames;
import ua.nure.ipz.zoo.dto.OrderDto;
import ua.nure.ipz.zoo.service.OrderService;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/order")
public class OrderController {

    private static final String EMPTY_STRING = "";

    @Resource
    private OrderService orderService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView viewOrder(HttpSession session, Model model) {
        OrderDto orderDto = SessionDataProvider.currentOrder(orderService, session);
        if (orderDto == null) {
            return returnModelWithErrorMessage(model);
        }

        model.addAttribute(ControllerConstants.OrderView.ORDER_ID, orderDto.getDomainId().toString());
        model.addAttribute(ControllerConstants.OrderView.TOTAL_PRICE, orderDto.getTotalPrice());
        model.addAttribute(ControllerConstants.OrderView.STATUS, orderDto.getOrderStatus());
        model.addAttribute(ControllerConstants.OrderView.NAME, orderDto.getCustomerName());
        model.addAttribute(ControllerConstants.OrderView.PHONE, orderDto.getCustomerPhone());
        model.addAttribute(ControllerConstants.OrderView.EMAIL, orderDto.getCustomerEmail());
        model.addAttribute(ControllerConstants.OrderView.PLACEMENT_TIME, orderDto.getPlacementTime());
        model.addAttribute(ControllerConstants.OrderView.COMMENT, orderDto.getComment());

        return new ModelAndView(ViewNames.ORDER, model.asMap());
    }

    private ModelAndView returnModelWithErrorMessage(Model model) {
        model.addAttribute(ControllerConstants.ErrorView.TITLE, ErrorKeys.NO_ORDER_TITLE);
        model.addAttribute(ControllerConstants.ErrorView.MESSAGE, ErrorKeys.NO_ORDER_MESSAGE);
        model.addAttribute(ControllerConstants.ErrorView.ARGUMENTS, EMPTY_STRING);

        return new ModelAndView(ViewNames.ERROR, model.asMap());
    }
}
