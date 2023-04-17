package ru.yandex.practicum.filmorate.storage.users;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IUserStorage {

    public User create(User user);

    public User update(User user);

    public boolean delete(Long id);

    public List<User> getAll();

    public Map<Long, User> getUserHashMap();

    public User getUser(Long id);

    public List<User> getUsersByIds(Collection<Long> ids);


    int getFriendShipStatus(Long selfId, Long friendId);

    List<User> getFriends(Long id);

    List<User> getMutalFriends(Long selfId, Long friendId);

    void addFriend(Long selfId, Long friendId);

    void acceptFriendShip(Long selfId, Long friendId);

    void deleteFriend(Long selfId, Long friendId);
}
