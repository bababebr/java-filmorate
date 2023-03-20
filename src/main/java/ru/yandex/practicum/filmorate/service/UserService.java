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
    private final Map<User, TreeSet<User>> friendshipGraph = new HashMap<>();
    @Getter
    private final IUserStorage userStorage;

    @Autowired
    public UserService(InMemoryUserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public List<User> getFriends(Long id) {
        User user = userStorage.getUser(id);
        if (friendshipGraph.containsKey(user)) {
            log.info("Друзья пользователя " + id + " :" + friendshipGraph.get(user));
            return new ArrayList<>(friendshipGraph.get(user));
        }
        throw new FriendServiceException("У пользователя нет друзей.");
    }

    public void addFriend(Long selfId, Long friendId) {
        User self = userStorage.getUser(selfId);
        User friend = userStorage.getUser(friendId);
        if (friendshipGraph.containsKey(self)) {
            if (friendshipGraph.get(self).contains(friend)) {
                throw new FriendServiceException("Пользователь " + friend + " уже в списке друзей.");
            } else {
                friendshipGraph.get(self).add(friend);
                log.info("У пользователя " + selfId + " были друзья и добавлся друг " + friendId);
            }
        } else {
            friendshipGraph.put(self, new TreeSet<>());
            friendshipGraph.get(self).add(friend);
            log.info("У пользователя " + selfId + " не было друзей. Добавлен 1-й друг: " + friendId);
        }
    }

    public boolean deleteFriend(Long selfId, Long friendId) {
        User self = userStorage.getUser(selfId);
        User friend = userStorage.getUser(friendId);
        if (!friendshipGraph.containsKey(self)) {
            throw new FriendServiceException("Пользователи " + self + " и " + friend + " не друзья");
        }
        friendshipGraph.get(self).remove(friend);
        friendshipGraph.get(friend).remove(self);
        log.info(self.getId() + " удалил из друзей " + friend.getId());
        return true;
    }

    public List<User> getMutualFriends(Long selfId, Long friendId) {
        User self = userStorage.getUser(selfId);
        User friend = userStorage.getUser(friendId);
        if (friendshipGraph.containsKey(self) && friendshipGraph.containsKey(friend)) {
            TreeSet<User> mutualFriendsSet = friendshipGraph.get(self);
            mutualFriendsSet.retainAll(friendshipGraph.get(friend));
            if (mutualFriendsSet.isEmpty()) {
                return new ArrayList<>();
            } else {
                return new ArrayList<>(mutualFriendsSet.stream().collect(Collectors.toList()));
            }
        }
        return new ArrayList<>();
    }
}
