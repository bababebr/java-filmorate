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
@RequestMapping("/films")
@Slf4j
public class FilmController {

    private final Map<Long, Film> filmList = new HashMap<>();
    private long id = 1;

    @PostMapping()
    public Film create(@Valid @RequestBody Film film) {
        if (filmList.containsKey(film.getId())) {
            log.warn("Попытка добавить существующий фильм: " + film);
            throw new IllegalArgumentException("Данный фильма уже существует.");
        }
        film.setId(id++);
        filmList.put(film.getId(), film);
        log.info("Добавлен фильм: " + film);
        return film;
    }

    @PutMapping()
    public Film update(@Valid @RequestBody Film film) {
        if (filmList.containsKey(film.getId())) {
            filmList.put(film.getId(), film);
            log.info("Обновлен фильм: " + film);
            return film;
        }
        log.warn("Попытка обновить несуществующий фильм: " + film);
        throw new NoSuchFilmException("Данного фильма нет");
    }

    @GetMapping()
    public List<Film> getAll() {
        return new ArrayList<>(filmList.values());
    }
}
