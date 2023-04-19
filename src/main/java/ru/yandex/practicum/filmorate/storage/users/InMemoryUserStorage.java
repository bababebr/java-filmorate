package ru.yandex.practicum.filmorate.storage.users;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NoSuchUserException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;

@Component
@Slf4j
public class InMemoryUserStorage implements IUserStorage {

    private Map<Long, User> userHashMap = new HashMap<>();
    private long id = 1;

    public User create(User user) {
        if (userHashMap.containsKey(user.getId())) {
            log.warn("Попытка добавить существующего юзера: " + user);
            throw new IllegalArgumentException("Данный юзер уже существует.");
        }
        user.setId(id++);
        userHashMap.put(user.getId(), user);
        log.info("Добавлен пользователь: " + user);
        return user;
    }

    public User update(User user) {

        if (userHashMap.containsKey(user.getId())) {
            userHashMap.put(user.getId(), user);
            log.info("Обновлен user: " + user);
            return user;
        }
        log.warn("Попытка обновить несуществующего пользователя: " + user);
        throw new NoSuchUserException("Данного пользователя не существует.");
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    public List<User> getAll() {
        return new ArrayList<>(userHashMap.values());
    }

    @Override
    public Map<Long, User> getUserHashMap() {
        return userHashMap;
    }

    @Override
    public User getUser(Long id) {
        User user = userHashMap.get(id);
        if (user == null) {
            throw new NoSuchUserException("Пользователь не существует.");
        }
        return user;
    }

    @Override
    public List<User> getUsersByIds(Collection<Long> ids) {
        List<User> returnList = new ArrayList<>();
        for (User u : userHashMap.values()) {
            if (ids.contains(u.getId())) {
                returnList.add(u);
            }
        }
        return returnList;
    }

    @Override
    public int getFriendShipStatus(Long selfId, Long friendId) {
        return 0;
    }

    @Override
    public List<User> getFriends(Long id) {
        return null;
    }

    @Override
    public List<User> getMutalFriends(Long selfId, Long friendId) {
        return null;
    }

    @Override
    public void addFriend(Long selfId, Long friendId) {

    }

    @Override
    public void acceptFriendShip(Long selfId, Long friendId) {

    }

    @Override
    public void deleteFriend(Long selfId, Long friendId) {

    }
}
