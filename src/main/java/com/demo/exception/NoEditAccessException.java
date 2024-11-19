package com.demo.exception;

public class NoEditAccessException extends RuntimeException {

    public NoEditAccessException(String message) {
        super(message);
    }
}