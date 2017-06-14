package ua.nure.ipz.zoo.service.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = {})
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@DecimalMin(value = "-10.00")
@DecimalMax(value = "25.00")
@ReportAsSingleViolation
public @interface Temperature {
    String message() default "Temperature value must be in range of [-10:25]";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}