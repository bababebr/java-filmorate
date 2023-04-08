package ru.yandex.practicum.filmorate.exception;

public class FriendServiceException extends RuntimeException{
    public FriendServiceException() {
        super();
    }

    public FriendServiceException(String message) {
        super(message);
    }
}
