package ua.nure.ipz.zoo.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import ua.nure.ipz.zoo.controller.constant.ControllerConstants;
import ua.nure.ipz.zoo.controller.constant.ErrorKeys;
import ua.nure.ipz.zoo.controller.constant.ViewNames;
import ua.nure.ipz.zoo.exception.ApplicationFatalError;
import ua.nure.ipz.zoo.exception.DomainLogicException;
import ua.nure.ipz.zoo.exception.ServiceValidationException;

@ControllerAdvice
public class ExceptionHandlingAdvice {
    @ExceptionHandler(ApplicationFatalError.class)
    public ModelAndView handle(ApplicationFatalError e) {
        ModelAndView mav = new ModelAndView(ViewNames.ERROR);
        mav.addObject(ControllerConstants.ErrorView.TITLE, ErrorKeys.SERVER_FATAL_ERROR_TITLE);
        mav.addObject(ControllerConstants.ErrorView.MESSAGE, ErrorKeys.SERVER_FATAL_ERROR_MESSAGE);
        mav.addObject(ControllerConstants.ErrorView.ARGUMENTS, "");

        return mav;
    }

    @ExceptionHandler(DomainLogicException.class)
    public ModelAndView handle(DomainLogicException e) {
        ModelAndView mav = new ModelAndView(ViewNames.ERROR);
        mav.addObject(ControllerConstants.ErrorView.TITLE, ErrorKeys.DOMAIN_LOGIC_ERROR_TITLE);
        mav.addObject(ControllerConstants.ErrorView.MESSAGE, ErrorKeys.DOMAIN_LOGIC_ERROR_MESSAGE);
        mav.addObject(ControllerConstants.ErrorView.ARGUMENTS, e.getMessage());

        return mav;
    }

    @ExceptionHandler(ServiceValidationException.class)
    public ModelAndView handle(ServiceValidationException e) {
        ModelAndView mav = new ModelAndView(ViewNames.ERROR);
        mav.addObject(ControllerConstants.ErrorView.TITLE, ErrorKeys.VALIDATION_ERROR_TITLE);
        mav.addObject(ControllerConstants.ErrorView.MESSAGE, ErrorKeys.VALIDATION_ERROR_MESSAGE);
        mav.addObject(ControllerConstants.ErrorView.ARGUMENTS, e.getMessage());

        return mav;
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ModelAndView handle(NoHandlerFoundException e) {
        ModelAndView mav = new ModelAndView(ViewNames.ERROR);
        mav.addObject(ControllerConstants.ErrorView.TITLE, ErrorKeys.NO_HANDLER_FOUND_TITLE);
        mav.addObject(ControllerConstants.ErrorView.MESSAGE, ErrorKeys.NO_HANDLER_FOUND_MESSAGE);
        mav.addObject(ControllerConstants.ErrorView.ARGUMENTS, e.getMessage());
        return mav;
    }
}