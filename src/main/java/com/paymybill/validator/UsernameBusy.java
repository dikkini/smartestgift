package com.paymybill.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * // TODO fill it
 */

@Documented
@Constraint(validatedBy = UsernameBusyValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface UsernameBusy {

    String message() default "{UsernameBusy.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
