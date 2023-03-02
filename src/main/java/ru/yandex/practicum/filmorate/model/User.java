package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@Data
public class User {
    @NotNull
    @EqualsAndHashCode.Exclude
    private long id;
    @NotNull
    @Email
    private String email;
    @NotNull
    @NotBlank
    private String login;
    private String name ="";
    @NotNull
    @Past
    private LocalDate birthday;
}
