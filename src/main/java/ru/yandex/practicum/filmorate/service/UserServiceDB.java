package ru.yandex.practicum.filmorate.service;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.users.IUserStorage;

import java.util.Collection;
import java.util.List;

@Service
@Slf4j
public class UserServiceDB {
    @Getter
    private final IUserStorage storage;

    @Autowired
    public UserServiceDB(@Qualifier("dbUserStorage") IUserStorage storage) {
        this.storage = storage;
    }

    public User create(User user) {
        return storage.create(user);
    }

    public User update(User user) {
        return storage.update(user);
    }

    public void delete(Long id) {
        storage.delete(id);
    }
    public User getUser(Long id) {
        return storage.getUser(id);
    }

    public List<User> getAll() {
        return storage.getAll();
    }
    public List<User> getUsersByIds(Collection<Long> id) {
        return storage.getUsersByIds(id);
    }

    public List<User> getFriends(Long id) {
        return storage.getFriends(id);
    }

    public void addFriend(Long selfId, Long friendId) {
        storage.addFriend(selfId, friendId);
    }

    public void deleteFriend(Long selfId, Long friendId) {
        storage.deleteFriend(selfId, friendId);
    }

    public List<User> getMutualFriends(Long selfId, Long friendId) {
        return storage.getMutalFriends(selfId, friendId);
    }


}
