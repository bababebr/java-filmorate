package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
public class FilmController {
    private final Set<Film> filmList = new HashSet<>();
    private long id = 0;
    @PostMapping(value = "/film")
    public Film addFilm(@RequestBody Film film) {
        film.setId(id++);
        filmList.add(film);
        return film;
    }

    @PutMapping(value = "/film")
    public Film updateFilm(@RequestBody Film film){
        boolean isFilmExist = filmList.contains(film) && filmList.remove(film);
    }
}
