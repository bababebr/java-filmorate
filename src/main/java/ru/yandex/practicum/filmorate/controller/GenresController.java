package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.FilmServiceDB;

import java.util.List;

@RestController
@RequestMapping("/genres")
@Slf4j
public class GenresController {
    private final FilmServiceDB filmService;

    @Autowired
    public GenresController(FilmServiceDB filmService) {
        this.filmService = filmService;
    }

    @GetMapping()
    public List<Genre> getAllGenres() {
        return filmService.getAllGenres();
    }

    @GetMapping("/{id}")
    public Genre getGenre(@PathVariable Long id) {
        return filmService.getGenre(id);
    }
}