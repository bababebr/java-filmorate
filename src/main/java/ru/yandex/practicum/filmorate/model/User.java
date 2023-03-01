package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDate;

@Data
public class User {
    @NonNull
    private long id;
    @NonNull
    private String email;
    @NonNull
    private String login;
    @NonNull
    private String name;
    @NonNull
    private LocalDate birthday;
}
