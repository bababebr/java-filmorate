package ru.yandex.practicum.filmorate.storage.users;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NoSuchUserException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        if(userHashMap.containsKey(user.getId())){
            userHashMap.put(user.getId(), user);
            log.info("Обновлен user: " + user);
            return user;
        }
        log.warn("Попытка обновить несуществующего пользователя: " + user);
        throw new NoSuchUserException("Данного пользователя не существует.");
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
        if(user == null){
            throw new NoSuchUserException("Пользователь не существует.");
        }
        return user;
    }
}
