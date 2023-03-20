package ru.yandex.practicum.filmorate.storage.users;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.exception.NoSuchUserException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IUserStorage {

    public User create(User user);
    public User update(User user);
    public List<User> getAll();
    public Map<Long, User> getUserHashMap();
    public User getUser(Long id);

}
