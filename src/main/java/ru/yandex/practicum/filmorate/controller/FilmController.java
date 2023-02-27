package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NoSuchFilmException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.*;

@RestController
@Slf4j
public class FilmController {

    private final static LocalDate OLDEST_RELEASE_DATE = LocalDate.of(1895, 12, 28);
    private final List<Film> filmList = new ArrayList<>();
    private long id = 1;

    @PostMapping(value = "/films")
    public Film create(@Valid @RequestBody Film film) {
        if(filmList.contains(film)) {throw new IllegalArgumentException("Данный фильма уже существует.");}
        film.setId(id);
        id++;
        filmList.add(film);
        return film;
    }

    @PutMapping(value = "/films")
    public Film update(@Valid @RequestBody Film film) {
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
