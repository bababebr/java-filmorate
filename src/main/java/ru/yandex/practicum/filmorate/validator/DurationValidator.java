package ru.yandex.practicum.filmorate.validator;

import ru.yandex.practicum.filmorate.annotaion.DurationIsPositive;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.Duration;

public class DurationValidator implements ConstraintValidator<DurationIsPositive, Duration> {
    @Override
    public boolean isValid(Duration duration, ConstraintValidatorContext constraintValidatorContext) {
        return !duration.isNegative();
    }
}
