package ru.yandex.practicum.filmorate.validator;

import ru.yandex.practicum.filmorate.annotaion.ReleaseDate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class ReleaseDateValidator implements ConstraintValidator<ReleaseDate, LocalDate> {
    private final static LocalDate OLDEST_RELEASE_DATE = LocalDate.of(1895, 12, 28);
    @Override
    public boolean isValid(LocalDate localDate, ConstraintValidatorContext constraintValidatorContext) {
        return localDate.isAfter(OLDEST_RELEASE_DATE);
    }
}
