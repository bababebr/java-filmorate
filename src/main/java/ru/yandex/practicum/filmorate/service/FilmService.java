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

import java.util.*;
import java.util.stream.Collectors;

@Service
@Getter
@Slf4j
public class FilmService {

    private final IFilmStorage filmStorage;

    @Autowired
    public FilmService(InMemoryFilmStorage filmStorage){
        this.filmStorage = filmStorage;
    }

    public boolean likeFilm(Long id, Long userId) {
        Film film = filmStorage.getFilm(id);
        if(!film.getLikedUsersId().add(userId)) {
            throw new FilmServiceException("Пользователь " + userId + " уже поставил Лайк.");
        }
        log.info("User " + userId + "поставил лайк фильму " + id);
        return true;
    }

    public void removeLike(Long id, Long userId){
        Film film = filmStorage.getFilm(id);
        try {
            if(!film.getLikedUsersId().remove(userId)){
                throw new FilmServiceException("Пользователь " + userId + " не лайкал фильм " + film);
            }
            log.info("User " + userId + " Убрал лайк с фильма " + id);
        } catch (NullPointerException e) {
            throw new NoSuchFilmException("Фильма не существует.");
        }
    }

    public List<Film> getTop(Collection<Film> films, Integer count) {
        if(count == null){
            count = 10;
        }
        count = count >= films.size() ? films.size() : count;
        TreeSet<Film> filmTreeSet = new TreeSet<>(filmStorage.getFilmsHashMap().values());
        ArrayList<Film> returnList = new ArrayList<>(filmTreeSet);
        log.info("Фильмы в порядке убывания лайков: " + filmTreeSet);
        if(returnList.isEmpty()) {return new ArrayList<>();}
        return returnList.subList(0, count);
    }
}
