package ru.yandex.practicum.filmorate.model;

import lombok.*;
import lombok.experimental.FieldNameConstants;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor(staticName = "create")
@ru.yandex.practicum.filmorate.annotaion.User
public class User implements Comparable<User> {
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
    private final LocalDate birthday;
    @EqualsAndHashCode.Exclude
    private final Set<Long> friendsIdSet = new HashSet<>();
    @EqualsAndHashCode.Exclude
    private final Set<Long> friendsStatus = new HashSet<>();

    @Override
    public int compareTo(User o) {
        if (id == o.getId()) {
            return 0;
        }
        return id > o.getId() ? 1 : -1;
    }
}
