package ru.yandex.practicum.filmorate.annotaion;

import ru.yandex.practicum.filmorate.validator.DurationValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DurationValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DurationIsPositive {
    String message() default  "Duration cannot be negative";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
