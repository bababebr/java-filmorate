package ru.yandex.practicum.filmorate.storage.users;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface IUserStorage {

    User create(User user);

    User update(User user);

    boolean delete(Long id);

    List<User> getAll();

    Map<Long, User> getUserHashMap();

    User getUser(Long id);

    List<User> getUsersByIds(Collection<Long> ids);


    int getFriendShipStatus(Long selfId, Long friendId);

    List<User> getFriends(Long id);

    List<User> getMutalFriends(Long selfId, Long friendId);

    void addFriend(Long selfId, Long friendId);

    void deleteFriend(Long selfId, Long friendId);
}
