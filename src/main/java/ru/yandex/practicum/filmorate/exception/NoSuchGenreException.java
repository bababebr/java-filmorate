package ru.yandex.practicum.filmorate.exception;

public class NoSuchGenreException extends RuntimeException {
    public NoSuchGenreException() {
        super();
    }

    public NoSuchGenreException(String message) {
        super(message);
    }
}
