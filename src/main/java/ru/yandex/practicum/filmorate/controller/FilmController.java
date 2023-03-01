package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NoSuchFilmException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.*;

@RestController
@Slf4j
public class FilmController {

    private final static LocalDate OLDEST_RELEASE_DATE = LocalDate.of(1895, 12, 28);
    private final List<Film> filmList = new ArrayList<>();
    private long id = 1;


    private boolean validateFilm(Film film) {
        if (film.getName().isBlank()) {
            throw new ValidationException("Название фильма не может быть пустым.");
        }
        if (film.getDescription().length() > 200) {
            throw new ValidationException("Описание фильма более 200 символов");
        }
        if (film.getReleaseDate().isBefore(OLDEST_RELEASE_DATE)) {
            throw new ValidationException(String.format("Дата релиза фильма не может быть раньше, чем: %t"
                    , OLDEST_RELEASE_DATE));
        }
        if (film.getDuration().isNegative()) {
            throw new ValidationException("Продолжительность фильма не может быть отрицательной.");
        }
        return true;
    }

    @PostMapping(value = "/films")
    public Film create(@RequestBody Film film) {
        validateFilm(film);
        if(filmList.contains(film)) {throw new IllegalArgumentException("Данный фильма уже существует.");}
        film.setId(id);
        id++;
        filmList.add(film);
        return film;
    }

    @PutMapping(value = "/films")
    public Film update(@RequestBody Film film) {
        validateFilm(film);
        Optional<Film> oldFilmO = filmList.stream().filter(f -> f.getId() == film.getId()).findFirst();
        if (oldFilmO.isPresent()) {
            filmList.remove(oldFilmO.get());
            film.setId(oldFilmO.get().getId());
            filmList.add(film);
            return film;
        } else throw new NoSuchFilmException("Данного фильма нет");
    }

    @GetMapping("/films")
    public List<Film> getAll() {
        return filmList;
    }
}
