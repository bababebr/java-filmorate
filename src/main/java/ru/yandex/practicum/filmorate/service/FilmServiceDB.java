package ru.yandex.practicum.filmorate.service;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.films.IFilmStorage;

import java.util.List;

@Service
@Getter
@Slf4j
public class FilmServiceDB {

    @Getter
    private final IFilmStorage storage;

    @Autowired
    public FilmServiceDB(@Qualifier("dbFilmStorage") IFilmStorage storage) {
        this.storage = storage;

    }

    public Film create(Film film) {
        return storage.create(film);
    }

    boolean delete(Long id) {
        return storage.delete(id);
    }

    public Film update(Film film) {
        return storage.update(film);
    }

    public List<Film> getAll() {
        return storage.getAll();
    }

    public Film getFilm(Long id) {
        return storage.getFilm(id);
    }

    public void likeFilm(Long id, Long userId) {
        storage.likeFilm(id, userId);
    }

    public void removeLike(Long id, Long userId) {
        storage.removeLike(id, userId);
    }

    public List<Film> getTop(Integer count) {
        return storage.getTop(count);
    }

    public List<Mpa> getAllMpa() {
        return storage.getAllMpa();
    }

    public Mpa getMpa(Long id) {
        return storage.getMpa(id);
    }

    public List<Genre> getAllGenres() {
        return storage.getAllGenres();
    }

    public Genre getGenre(Long id) {
        return storage.getGenre(id);
    }
}
