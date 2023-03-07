package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@Data
@ru.yandex.practicum.filmorate.annotaion.User
public class User {
    @NotNull
    @EqualsAndHashCode.Exclude
    private long id;
    @NotNull
    @Email(message = "Неверный e-mail.")
    private String email;
    @NotNull
    @NotBlank(message = "Login не может быть пустым.")
    private String login;
    private String name;
    @NotNull
    @Past(message = "День рождения введен неверно.")
    private LocalDate birthday;
}
