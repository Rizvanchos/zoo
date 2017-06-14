package ua.nure.ipz.zoo.aspect;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Order(2)
public class ServiceMethodLoggingAspect {
    private static final Logger log = LogManager.getLogger("zoo");
    private static final Logger logErrors = LogManager.getLogger("zoo-errors");

    @Before(value = "@within(org.springframework.stereotype.Service) || @annotation(org.springframework.stereotype.Service)")
    public void before(JoinPoint joinPoint) throws Throwable {
        log.debug(String.format("START %s.%s\n", joinPoint.getTarget().getClass().getName(), joinPoint.getSignature().getName()));
    }

    @After(value = "@within(org.springframework.stereotype.Service) || @annotation(org.springframework.stereotype.Service)")
    public void after(JoinPoint joinPoint) throws Throwable {
        log.debug(String.format("FINISH %s.%s\n", joinPoint.getTarget().getClass().getName(), joinPoint.getSignature().getName()));
    }

    @AfterThrowing(value = "@within(org.springframework.stereotype.Service) || @annotation(org.springframework.stereotype.Service)", throwing = "ex")
    public void handleException(JoinPoint joinPoint, Exception ex) throws Throwable {
        logErrors.catching(ex);
    }
}
