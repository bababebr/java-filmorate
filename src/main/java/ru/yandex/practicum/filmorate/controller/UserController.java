package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NoSuchUserException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.*;

@RestController
@Slf4j
public class UserController {
    private List<User> userList = new ArrayList<>();
    private long id = 1;

    @PostMapping(value = "/users")
    public User create(@Valid @RequestBody User user) {
        if (userList.contains(user)) {
            throw new IllegalArgumentException("Данный пользовательн уже существует");
        }
        user.setId(id);
        id++;
        userList.add(user);
        return user;
    }

    @PutMapping(value = "/users")
    public User update(@Valid  @RequestBody User user) {
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
