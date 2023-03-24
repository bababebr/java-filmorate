package ru.yandex.practicum.filmorate.service;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.FriendServiceException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.users.IUserStorage;
import ru.yandex.practicum.filmorate.storage.users.InMemoryUserStorage;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService {
    @Getter
    private final IUserStorage userStorage;

    @Autowired
    public UserService(InMemoryUserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public List<User> getFriends(Long id) {
        User user = userStorage.getUser(id);
        log.info("Друзья пользователя " + id + " :" + user);
        return userStorage.getUsersByIds(user.getFriendsIdSet());
    }

    public void addFriend(Long selfId, Long friendId) {
        User self = userStorage.getUser(selfId);
        User friend = userStorage.getUser(friendId);
        if (self.getFriendsIdSet().contains(friendId) || friend.getFriendsIdSet().contains(selfId)) {
            throw new FriendServiceException("Пользователь " + friend + " уже в списке друзей.");
        } else {
            self.getFriendsIdSet().add(friendId);
            friend.getFriendsIdSet().add(selfId);
            log.info("Пользователь c id " + self.getId() + " добавил в друзья пользователя с id " + friend.getId());
        }
    }

    public boolean deleteFriend(Long selfId, Long friendId) {
        User self = userStorage.getUser(selfId);
        User friend = userStorage.getUser(friendId);
        if (!self.getFriendsIdSet().contains(friendId)) {
            throw new FriendServiceException("Пользователи " + self + " и " + friend + " не друзья");
        }
        self.getFriendsIdSet().remove(friendId);
        friend.getFriendsIdSet().remove(selfId);
        log.info(self.getId() + " удалил из друзей " + friend.getId());
        return true;
    }

    public List<User> getMutualFriends(Long selfId, Long friendId) {
        User self = userStorage.getUser(selfId);
        User friend = userStorage.getUser(friendId);
        TreeSet<User> mutualFriendsSet = new TreeSet<>(userStorage.getUsersByIds(self.getFriendsIdSet()));
        mutualFriendsSet.retainAll(userStorage.getUsersByIds(friend.getFriendsIdSet()));
        if (mutualFriendsSet.isEmpty()) {
            return new ArrayList<>();
        } else {
            return new ArrayList<>(mutualFriendsSet.stream().collect(Collectors.toList()));
        }
    }

}
