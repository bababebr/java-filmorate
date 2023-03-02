package ru.yandex.practicum.filmorate.annotaion;

import ru.yandex.practicum.filmorate.validator.UserValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UserValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface User {

    String message() default  "User name cannot be empty. Login will be used as Name";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
