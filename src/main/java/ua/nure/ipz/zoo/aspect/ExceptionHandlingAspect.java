package ua.nure.ipz.zoo.aspect;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import ua.nure.ipz.zoo.exception.ApplicationFatalError;
import ua.nure.ipz.zoo.exception.DomainLogicException;
import ua.nure.ipz.zoo.exception.ServiceUnresolvedEntityException;
import ua.nure.ipz.zoo.exception.ServiceValidationException;

@Component
@Aspect
@Order(1)
public class ExceptionHandlingAspect {
    @Around(value = "@within(org.springframework.stereotype.Service) || @annotation(org.springframework.stereotype.Service)")
    public Object interceptCall(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            return joinPoint.proceed();
        } catch (DomainLogicException | ServiceUnresolvedEntityException ex) {
            throw ex;
        } catch (ConstraintViolationException ex) {
            StringBuilder builder = new StringBuilder();
            builder.append("Data validation error\n");

            for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
                builder.append(violation);
                builder.append("\n");
            }

            throw new ServiceValidationException(builder.toString());
        } catch (Exception ex) {
            throw new ApplicationFatalError();
        }
    }
}
