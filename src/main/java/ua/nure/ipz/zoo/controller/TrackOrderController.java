package ua.nure.ipz.zoo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.ModelAndView;
import ua.nure.ipz.zoo.controller.constant.ControllerConstants;
import ua.nure.ipz.zoo.controller.constant.ErrorKeys;
import ua.nure.ipz.zoo.controller.constant.ViewNames;
import ua.nure.ipz.zoo.dto.OrderDto;
import ua.nure.ipz.zoo.service.OrderService;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.UUID;

@Controller
@RequestMapping("/trackorder")
public class TrackOrderController {
    @Resource
    private OrderService orderService;

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView submitTrackOrder(HttpSession session, @RequestParam UUID inputTrackOrderId, @RequestParam String inputTrackOrderEmail,
                                         Model model) {
        OrderDto orderDto = orderService.findOrder(inputTrackOrderId);
        if (orderDto == null) {
            return returnModelWithErrorMessage(model, ErrorKeys.ORDER_NOT_FOUND_TITLE,ErrorKeys.ORDER_NOT_FOUND_MESSAGE, inputTrackOrderId.toString());
        } else if (!orderDto.getCustomerEmail().equalsIgnoreCase(inputTrackOrderEmail)) {
            return returnModelWithErrorMessage(model, ErrorKeys.ORDER_ACCESS_VIOLATION_TITLE, ErrorKeys.ORDER_ACCESS_VIOLATION_MESSAGE, inputTrackOrderEmail);
        }

        SessionDataProvider.setCurrentOrderId(session, inputTrackOrderId);
        return new ModelAndView("redirect:/order/");
    }

    private ModelAndView returnModelWithErrorMessage(Model model, String title, String message, String attributeValue) {
        model.addAttribute(ControllerConstants.ErrorView.TITLE, title);
        model.addAttribute(ControllerConstants.ErrorView.MESSAGE, message);
        model.addAttribute(ControllerConstants.ErrorView.ARGUMENTS, attributeValue);

        return new ModelAndView(ViewNames.ERROR, model.asMap());
    }
}
