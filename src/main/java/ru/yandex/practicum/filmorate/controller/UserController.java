package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.FriendServiceException;
import ru.yandex.practicum.filmorate.exception.NoSuchUserException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.users.IUserStorage;
import ru.yandex.practicum.filmorate.storage.users.InMemoryUserStorage;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public User create(@Valid @RequestBody User user) {
        return userService.getUserStorage().create(user);
    }

    @PutMapping
    public User update(@Valid @RequestBody User user) {
        return userService.getUserStorage().update(user);
    }

    @GetMapping
    public List<User> getAll() {
        return userService.getUserStorage().getAll();
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id){
       return userService.getUserStorage().getUser(id);
    }
    @PutMapping("/{id}/friends/{friendId}")
    public void addFriend(@PathVariable long id, @PathVariable long friendId) {
        userService.addFriend(id, friendId);
        userService.addFriend(friendId, id);
    }
    @DeleteMapping("/{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable Long id, @PathVariable Long friendId) {
        userService.deleteFriend(id, friendId);
    }

    @GetMapping("/{id}/friends")
    public List<User> getFriends(@PathVariable Long id){
       return userService.getFriends(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> getMutualFriend(@PathVariable Long id, @PathVariable Long otherId){
        return userService.getMutualFriends(id, otherId);
    }

    @ExceptionHandler({NoSuchUserException.class, FriendServiceException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> userControllerException(final RuntimeException e) {
        return Map.of("Ошибка Пользователя: ", e.getMessage());
    }

}
