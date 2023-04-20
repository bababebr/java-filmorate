package ru.yandex.practicum.filmorate.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor(staticName = "create")
@FieldDefaults(makeFinal=true, level= AccessLevel.PRIVATE)
@ru.yandex.practicum.filmorate.annotaion.User
public class User implements Comparable<User> {
    @NotNull
    @NonFinal
    @EqualsAndHashCode.Exclude
    long id;
    @NotNull
    @NonFinal
    @Email(message = "Неверный e-mail.")
    String email;
    @NotNull
    @NonFinal
    @NotBlank(message = "Login не может быть пустым.")
    String login;
    @NonFinal
    String name;
    @NotNull
    @Past(message = "День рождения введен неверно.")
    LocalDate birthday;
    @EqualsAndHashCode.Exclude
    Set<Long> friendsIdSet = new HashSet<>();
    @EqualsAndHashCode.Exclude
    Set<Long> friendsStatus = new HashSet<>();

    @Override
    public int compareTo(User o) {
        if (id == o.getId()) {
            return 0;
        }
        return id > o.getId() ? 1 : -1;
    }
}
