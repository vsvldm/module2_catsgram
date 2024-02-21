package ru.yandex.practicum.catsgram.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.catsgram.exception.*;
import ru.yandex.practicum.catsgram.model.ErrorResponse;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handlIncorrectParameterException(final IncorrectParameterException e) {
        return new ErrorResponse(String.format("Ошибка с полем %s", e.getParameter()));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleInvalidEmailException(final InvalidEmailException e) {
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleUserAlreadyExistException(final UserAlreadyExistException e) {
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler({PostNotFoundException.class, UserNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundException(final RuntimeException e) {
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleThrowable(final Throwable e) {
        return new ErrorResponse("Произошла непредвиденная ошибка.");
    }
}
