package com.jsya.dongbu.common.exception;

public class NotFoundException extends RuntimeException{

    public NotFoundException() {
        super();
    }

    public NotFoundException(String message) {
        super(message);
    }
}
