package com.paymybill.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EmailBusyValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface EmailBusy {

    String message() default "{EmailBusy.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
