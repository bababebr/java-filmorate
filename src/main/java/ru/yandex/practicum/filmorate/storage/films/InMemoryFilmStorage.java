package ru.yandex.practicum.filmorate.storage.films;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NoSuchFilmException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class InMemoryFilmStorage implements IFilmStorage {
    private final Map<Long, Film> filmHashMap = new HashMap<>();
    private long id = 1;

    @Override
    public Film create(Film film) {
        if (filmHashMap.containsKey(film.getId())) {
            log.warn("Попытка добавить существующий фильм: " + film);
            throw new IllegalArgumentException("Данный фильма уже существует.");
        }
        film.setId(id++);
        filmHashMap.put(film.getId(), film);
        log.info("Добавлен фильм: " + film);
        return film;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public Film update(Film film) {
        if (filmHashMap.containsKey(film.getId())) {
            filmHashMap.put(film.getId(), film);
            log.info("Обновлен фильм: " + film);
            return film;
        }
        log.warn("Попытка обновить несуществующий фильм: " + film);
        throw new NoSuchFilmException("Данного фильма нет");
    }

    @Override
    public List<Film> getAll() {
        return new ArrayList<>(filmHashMap.values());
    }

    @Override
    public Film getFilm(Long id) {
        Film film = filmHashMap.get(id);
        if (film == null) {
            throw new NoSuchFilmException("Фильм не найден.");
        }
        return film;
    }

    @Override
    public void likeFilm(Long id, Long userId) {

    }

    @Override
    public void removeLike(Long id, Long userId) {

    }

    @Override
    public List<Film> getTop(Integer count) {
        return null;
    }

    @Override
    public List<Mpa> getAllMpa() {
        return null;
    }

    @Override
    public Mpa getMpa(Long filmId) {
        return null;
    }

    @Override
    public Genre getGenre(Long genreId) {
        return null;
    }

    @Override
    public List<Genre> getAllGenres() {
        return null;
    }
}
