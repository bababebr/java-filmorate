package ru.yandex.practicum.filmorate.exception;

public class NoSuchMPAException extends RuntimeException{
    public NoSuchMPAException() {
        super();
    }

    public NoSuchMPAException(String message) {
        super(message);
    }
}
