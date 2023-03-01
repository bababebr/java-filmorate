package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NoSuchFilmException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@RestController
@Slf4j
public class FilmController {

    private final static LocalDate OLDEST_RELEASE_DATE = LocalDate.of(1895, 12, 28);
    private final Set<Film> filmSet = new HashSet<>();
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

    @PostMapping(value = "/film")
    public Film addFilm(@RequestBody Film film) {
        validateFilm(film);
        if(filmSet.contains(film)) {throw new IllegalArgumentException("Данный фильма уже существует.");}
        film.setId(id);
        id++;
        filmSet.add(film);
        return film;
    }

    @PutMapping(value = "/film")
    public Film updateFilm(@RequestBody Film film) {
        validateFilm(film);
        if (filmSet.contains(film)) {
            filmSet.remove(film);
            filmSet.add(film);
            return film;
        } else throw new NoSuchFilmException("Данного фильма нет");
    }

    @GetMapping("/film")
    public Set<Film> getAllFilms() {
        return filmSet;
    }
}
