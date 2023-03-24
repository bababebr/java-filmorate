package ru.yandex.practicum.filmorate.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleHappinessOverflow(final ValidationException e) {
        return Map.of("Объект не прошел валидацию: ", e.getMessage());
    }

    @ExceptionHandler({NoSuchUserException.class, FriendServiceException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> userControllerException(final RuntimeException e) {
        return Map.of("Ошибка Пользователя: ", e.getMessage());
    }

    @ExceptionHandler({NoSuchFilmException.class, FilmServiceException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> filmControllerException(final RuntimeException e) {
        return Map.of("Ошибка Фильмов: ", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> serverException(final Exception e) {
        return Map.of("Ошибка сервера: ", e.getMessage());
    }
}
