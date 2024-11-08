package com.parquimetro.domain.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = ValuePositiveValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValuePositive {
    String message() default "O valor deve ser positivo";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
