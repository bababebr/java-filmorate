package ru.yandex.practicum.filmorate.service;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.FilmServiceException;
import ru.yandex.practicum.filmorate.exception.NoSuchFilmException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.films.IFilmStorage;
import ru.yandex.practicum.filmorate.storage.films.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.users.IUserStorage;
import ru.yandex.practicum.filmorate.storage.users.InMemoryUserStorage;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Getter
@Slf4j
public class FilmService {

    private final IFilmStorage filmStorage;
    private final IUserStorage userStorage;

    @Autowired
    public FilmService(InMemoryFilmStorage filmStorage, InMemoryUserStorage userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }

    public boolean likeFilm(Long id, Long userId) {
        userStorage.getUser(userId);
        Film film = filmStorage.getFilm(id);
        if (!film.getLikedUsersId().add(userId)) {
            throw new FilmServiceException("Пользователь " + userId + " уже поставил Лайк.");
        }
        log.info("User " + userId + "поставил лайк фильму " + id);
        return true;
    }

    public void removeLike(Long id, Long userId) {
        Film film = filmStorage.getFilm(id);
        userStorage.getUser(userId);
        if (!film.getLikedUsersId().remove(userId)) {
            throw new FilmServiceException("Пользователь " + userId + " не лайкал фильм " + film);
        }
        log.info("User " + userId + " Убрал лайк с фильма " + id);
    }

    public List<Film> getTop(Integer count) {
        TreeSet<Film> filmTreeSet = new TreeSet<>(filmStorage.getAll());
        ArrayList<Film> returnList = new ArrayList<>(filmTreeSet);

        if (returnList.isEmpty()) {
            return new ArrayList<>();
        }

        count = count >= filmTreeSet.size() ? filmTreeSet.size() : count;
        return returnList.subList(0, count);
    }
}
