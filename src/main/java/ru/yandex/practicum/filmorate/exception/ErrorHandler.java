package ru.yandex.practicum.filmorate.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.filmorate.errors.ErrorResponse;

import javax.validation.ValidationException;
import java.util.Map;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleHappinessOverflow(final ValidationException e) {
        return new ErrorResponse("Объект не прошел валидацию: ", e.getMessage());
    }

    @ExceptionHandler({NoSuchUserException.class, FriendServiceException.class, NoSuchMPAException.class,
            NoSuchGenreException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse userControllerException(final RuntimeException e) {
        return new ErrorResponse("Ошибка Пользователя: ", e.getMessage());
    }

    @ExceptionHandler({NoSuchFilmException.class, FilmServiceException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse filmControllerException(final RuntimeException e) {
        return new ErrorResponse("Ошибка Фильмов: ", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse serverException(final Exception e) {
        return new ErrorResponse("Ошибка сервера: ", e.getMessage());
    }
}
