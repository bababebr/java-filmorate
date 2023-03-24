package ru.yandex.practicum.filmorate.storage.films;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface IFilmStorage {
    public Film create(Film film);

    public Film update(Film film);

    public List<Film> getAll();

    public Film getFilm(Long id);
}
