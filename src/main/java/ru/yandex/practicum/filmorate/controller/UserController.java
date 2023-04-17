package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserServiceDB;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    private final UserServiceDB userServiceDB;

    @Autowired
    public UserController(UserServiceDB userServiceDB) {
        this.userServiceDB = userServiceDB;
    }

    @PostMapping
    public User create(@Valid @RequestBody User user) {
        return userServiceDB.create(user);
    }

    @PutMapping
    public User update(@Valid @RequestBody User user) {
        return userServiceDB.update(user);
    }

    @GetMapping
    public List<User> getAll() {
        return userServiceDB.getAll();
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id){
       return userServiceDB.getUser(id);
    }
    @PutMapping("/{id}/friends/{friendId}")
    public void addFriend(@PathVariable long id, @PathVariable long friendId) {
        userServiceDB.addFriend(id, friendId);
    }
    @DeleteMapping("/{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable Long id, @PathVariable Long friendId) {
        userServiceDB.deleteFriend(id, friendId);
    }

    @DeleteMapping("{id}")
    public void deleteUser(@PathVariable Long id){
        userServiceDB.delete(id);
    }

    @GetMapping("/{id}/friends")
    public List<User> getFriends(@PathVariable Long id){
       return userServiceDB.getFriends(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> getMutualFriend(@PathVariable Long id, @PathVariable Long otherId){
        return userServiceDB.getMutualFriends(id, otherId);
    }

}
