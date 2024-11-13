package com.parquimetro.domain.validator;

import org.junit.jupiter.api.Test;

import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

class ValuePositiveValidatorTest {

    @Test
    void testIsValidWithPositiveValue() {
        ValuePositiveValidator validator = new ValuePositiveValidator();
        ConstraintValidatorContext context = mock(ConstraintValidatorContext.class);

        BigDecimal positiveValue = new BigDecimal("10.50");

        assertTrue(validator.isValid(positiveValue, context));
    }

    @Test
    void testIsValidWithZeroValue() {
        ValuePositiveValidator validator = new ValuePositiveValidator();
        ConstraintValidatorContext context = mock(ConstraintValidatorContext.class);

        BigDecimal zeroValue = BigDecimal.ZERO;

        assertFalse(validator.isValid(zeroValue, context));
    }

    @Test
    void testIsValidWithNegativeValue() {
        ValuePositiveValidator validator = new ValuePositiveValidator();
        ConstraintValidatorContext context = mock(ConstraintValidatorContext.class);

        BigDecimal negativeValue = new BigDecimal("-10.50");

        assertFalse(validator.isValid(negativeValue, context));
    }

    @Test
    void testIsValidWithNullValue() {
        ValuePositiveValidator validator = new ValuePositiveValidator();
        ConstraintValidatorContext context = mock(ConstraintValidatorContext.class);

        assertFalse(validator.isValid(null, context));
    }
}
