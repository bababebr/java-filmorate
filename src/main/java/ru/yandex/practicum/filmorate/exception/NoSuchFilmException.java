package ru.yandex.practicum.filmorate.exception;

public class NoSuchFilmException extends RuntimeException{
    public NoSuchFilmException() {
        super();
    }

    public NoSuchFilmException(String message) {
        super(message);
    }
}
