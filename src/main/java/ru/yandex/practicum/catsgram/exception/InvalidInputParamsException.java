package ru.yandex.practicum.catsgram.exception;

public class InvalidInputParamsException extends RuntimeException{
    public InvalidInputParamsException(String message) {
        super(message);
    }
}
