package ru.yandex.practicum.filmorate.validator;

import ru.yandex.practicum.filmorate.annotaion.User;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UserValidator implements ConstraintValidator<User, ru.yandex.practicum.filmorate.model.User> {
    @Override
    public boolean isValid(ru.yandex.practicum.filmorate.model.User user, ConstraintValidatorContext constraintValidatorContext) {
        if(user.getName().isBlank()){
            user.setName(user.getLogin());
        }
        return true;
    }
}
