package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NoSuchUserException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.*;

@RestController
@Slf4j
public class UserController {
    private List<User> userList = new ArrayList<>();
    private long id = 1;

    private boolean validateUser(User user) {
        if (user.getEmail().isBlank() || !user.getEmail().contains("@")) {
            throw new ValidationException("Неверный e-mail.");
        }
        if (user.getLogin().isBlank() || user.getLogin().contains(" ")) {
            throw new ValidationException("Неверный Login.");
        }
        if (user.getName().isBlank()) user.setName(user.getLogin());
        if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException("День рождения указан неверно");
        }
        return true;
    }

    @PostMapping(value = "/users")
    public User create(@RequestBody User user) {
        validateUser(user);
        if (userList.contains(user)) {
            throw new IllegalArgumentException("Данный пользовательн уже существует");
        }
        user.setId(id);
        id++;
        userList.add(user);
        return user;
    }

    @PutMapping(value = "/users")
    public User update(@RequestBody User user) {
        validateUser(user);
        Optional<User> oldUser = userList.stream().filter(u -> u.getId() == user.getId()).findFirst();
        if(oldUser.isPresent()){
            userList.remove(oldUser.get());
            user.setId(oldUser.get().getId());
            userList.add(user);
            return user;
        }
        else throw new NoSuchUserException("Данного пользователя не существует.");
    }

    @GetMapping("/users")
    public List<User> getAll() {
        return userList;
    }

}
