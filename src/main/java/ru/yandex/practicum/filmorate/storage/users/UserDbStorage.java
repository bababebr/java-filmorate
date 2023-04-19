package ru.yandex.practicum.filmorate.storage.users;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.FriendServiceException;
import ru.yandex.practicum.filmorate.exception.NoSuchUserException;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@Component("dbUserStorage")
@Slf4j
public class UserDbStorage implements IUserStorage {
    private final JdbcTemplate jdbcTemplate;
    private int id = 0;


    public UserDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User create(User user) {
        user.setId(++id);
        jdbcTemplate.update("INSERT INTO USERS (EMAIL, LOGIN, NAME, BIRTHDAY) " +
                "VALUES (?,?,?,?)", user.getEmail(), user.getLogin(), user.getName(), user.getBirthday());
        return user;
    }

    @Override
    public boolean delete(Long id) {
        return jdbcTemplate.update("DELETE FROM USERS WHERE ID = ?", id) > 0;
    }

    @Override
    public User update(User user) {
        int result = jdbcTemplate.update("UPDATE USERS SET EMAIL = ?, LOGIN = ?, NAME = ?, BIRTHDAY = ? WHERE ID = ?",
                user.getEmail(), user.getLogin(), user.getName(), user.getBirthday(), user.getId());
        if (result != 0) {
            return user;
        } else {
            throw new NoSuchUserException(String.format("Неудалось обновить пользователя. " +
                    "Такого пользователя с ID %d не существует", user.getId()));
        }
    }

    @Override
    public List<User> getAll() {
        String sql = "SELECT * FROM USERS";
        return jdbcTemplate.query(sql, (rs, rowNum) -> makeUser(rs));
    }

    @Override
    public Map<Long, User> getUserHashMap() {
        return null;
    }

    @Override
    public User getUser(Long id) {
        String sql = String.format("SELECT * FROM USERS WHERE ID = %s", id);
        List<User> result = jdbcTemplate.query(sql, (rs, rowNum) -> makeUser(rs));
        if (!result.isEmpty()) {
            return result.get(0);
        } else {
            log.info("Пользователь с ID {} не найден", id);
            throw new NoSuchUserException("Пользователь с ID " + id + " не найден");
        }
    }

    @Override
    public List<User> getUsersByIds(Collection<Long> ids) {
        String inParams = ids.stream().map(id -> "?").collect(Collectors.joining(","));
        return jdbcTemplate.query(String.format("SELECT * FROM USERS WHERE ID IN (%s)", inParams), ids.toArray(),
                ((rs, rowNum) -> makeUser(rs)));
    }

    @Override
    public int getFriendShipStatus(Long selfId, Long friendId) {
        if (selfId.equals(friendId) || friendId < 1 || selfId < 1) {
            throw new NoSuchUserException();
        }
        String sql = String.format("SELECT Status_id FROM FRIENDSHIP WHERE USER_ID =%d AND Friend_ID =%d", selfId, friendId);
        List<Integer> result = jdbcTemplate.query(sql, (rs, rowNum) -> makeInt(rs));
        if (result.isEmpty()) {
            return 2;
        }
        return 1;
    }

    @Override
    public List<User> getFriends(Long id) {
        String subSql = String.format("SELECT * FROM Friendship WHERE User_id = %s AND Status_id = 1", id);
        List<Long> friendsIds = jdbcTemplate.query(subSql, (rs, rowNum) -> makeLong(rs));
        return getUsersByIds(friendsIds);
    }

    @Override
    public List<User> getMutalFriends(Long selfId, Long friendId) {

        TreeSet<User> mutualFriendsSet = new TreeSet<>(getFriends(selfId));
        mutualFriendsSet.retainAll(getFriends(friendId));
        if (mutualFriendsSet.isEmpty()) {
            return new ArrayList<>();
        } else {
            return new ArrayList<>(new ArrayList<>(mutualFriendsSet));
        }
    }

    @Override
    public void addFriend(Long selfId, Long friendId) {
        int friendshipStatus = getFriendShipStatus(selfId, friendId);
        switch (friendshipStatus) {
            case 1:
                throw new FriendServiceException(String.format("Пользователь %d уже в друзьях",
                        friendId));
            case 2:
                jdbcTemplate.update("INSERT INTO FRIENDSHIP(User_id, FRIEND_ID, STATUS_ID) " +
                        "VALUES (?, ?, 1)", selfId, friendId);
                break;
        }
    }

    @Override
    public void acceptFriendShip(Long selfId, Long friendId) {

    }

    @Override
    public void deleteFriend(Long selfId, Long friendId) {
        jdbcTemplate.update("DELETE FROM FRIENDSHIP WHERE USER_ID = ? AND FRIEND_ID = ?", selfId, friendId);
    }

    private User findUser(User user) {
        for (User u : this.getAll()) {
            if (u.equals(user)) {
                return u;
            }
        }
        throw new NoSuchUserException("Польватель не найден.");
    }

    private User makeUser(ResultSet rs) throws SQLException {
        return User.create(rs.getInt("id"),
                rs.getString("email"),
                rs.getString("login"),
                rs.getString("name"),
                rs.getDate("birthday").toLocalDate());
    }

    private Long makeLong(ResultSet rs) throws SQLException {
        return rs.getLong("friend_id");
    }

    private int makeInt(ResultSet rs) throws SQLException {
        return rs.getInt("status_id");
    }
}
