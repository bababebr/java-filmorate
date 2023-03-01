package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NoSuchUserException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@RestController
@Slf4j
public class UserController {
    private Set<User> usersSet = new HashSet<>();
    private long id = 1;

    private boolean validateUser(User user){
        if(user.getEmail().isBlank() || !user.getEmail().contains("@")) {
            throw new ValidationException("Неверный e-mail.");
        }
        if(user.getLogin().isBlank() || user.getLogin().contains(" ")) {
            throw new ValidationException("Неверный Login.");
        }
        if(user.getName().isBlank()) user.setName(user.getLogin());
        if(user.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException("День рождения указан неверно");
        }
        return true;
    }
    @PostMapping(value = "/users")
    public User addUser(@RequestBody User user){
        validateUser(user);
        if(usersSet.contains(user)){
            throw new IllegalArgumentException("Данный пользовательн уже существует");
        }
        user.setId(id);
        id++;
        usersSet.add(user);
        return user;
    }

    @PutMapping(value = "/users")
    public User updateUser(@RequestBody User user){
        validateUser(user);
        if(usersSet.contains(user)){
            usersSet.remove(user);
            usersSet.add(user);
            return user;
        }
        else throw new NoSuchUserException("Данного пользователя не существует.");
    }

    @GetMapping("/users")
    public Object[] getAllUsers(){
        return usersSet.toArray();
    }

}
