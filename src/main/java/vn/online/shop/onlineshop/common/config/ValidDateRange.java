package vn.online.shop.onlineshop.common.config;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import javax.lang.model.element.Element;
import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DateRangeValidator.class)
@Documented
public @interface ValidDateRange {
    String message() default "Start date must be before end date";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
