package com.parquimetro.domain.validator;

import org.junit.jupiter.api.Test;

import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

class DateAfterTodayValidatorTest {

    @Test
    void testIsValidWithFutureDate() {
        DateAfterTodayValidator validator = new DateAfterTodayValidator();
        ConstraintValidatorContext context = mock(ConstraintValidatorContext.class);

        LocalDateTime futureDate = LocalDateTime.now().plusDays(1);

        assertTrue(validator.isValid(futureDate, context));
    }

    @Test
    void testIsValidWithPastDate() {
        DateAfterTodayValidator validator = new DateAfterTodayValidator();
        ConstraintValidatorContext context = mock(ConstraintValidatorContext.class);

        LocalDateTime pastDate = LocalDateTime.now().minusDays(1);

        assertFalse(validator.isValid(pastDate, context));
    }

    @Test
    void testIsValidWithNullDate() {
        DateAfterTodayValidator validator = new DateAfterTodayValidator();
        ConstraintValidatorContext context = mock(ConstraintValidatorContext.class);

        assertFalse(validator.isValid(null, context));
    }
}
