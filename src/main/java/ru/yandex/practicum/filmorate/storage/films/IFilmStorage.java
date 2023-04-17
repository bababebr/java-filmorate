package ru.yandex.practicum.filmorate.storage.films;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface IFilmStorage {
    public Film create(Film film);

    boolean delete(Long id);

    public Film update(Film film);

    public List<Film> getAll();

    public Film getFilm(Long id);

    void likeFilm(Long id, Long userId);

    void removeLike(Long id, Long userId);

    List<Film> getTop(Integer count);

    List<Mpa> getAllMpa();

    Mpa getMpa(Long filmId);

    Genre getGenre(Long genreId);

    List<Genre> getAllGenres();
}
