package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.films.InMemoryFilmStorage;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {
    private final FilmService filmService;

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @PostMapping()
    public Film create(@Valid @RequestBody Film film) {
        return filmService.getFilmStorage().create(film);
    }

    @PutMapping()
    public Film update(@Valid @RequestBody Film film) {
        return filmService.getFilmStorage().update(film);
    }

    @PutMapping("/{id}/like/{userId}")
    public void like(@PathVariable Long id, @PathVariable Long userId) {
        filmService.likeFilm(id, userId);
    }
    @DeleteMapping("/{id}/like/{userId}")
    public void unlike(@PathVariable Long id, @PathVariable Long userId) {
        filmService.removeLike(id, userId);
    }
    @GetMapping("/popular")
    public List<Film> getFilmsTop(@RequestParam(required = false) Integer count){
        return filmService.getTop(filmService.getFilmStorage().getAll(), count);
    }

    @GetMapping("/{id}")
    public Film getFilm(@PathVariable Long id) {
        return filmService.getFilmStorage().getFilm(id);
    }
    @GetMapping()
    public List<Film> getAll() {
        return filmService.getFilmStorage().getAll();
    }

    @ExceptionHandler({NoSuchFilmException.class, FilmServiceException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> userControllerException(final RuntimeException e) {
        return Map.of("Ошибка Фильмов: ", e.getMessage());
    }
}
