package com.mertdev.therawdata.exceptions;

public class BasketException extends RuntimeException {
    public BasketException(String message) {
        super(message);
    }

    public BasketException(String message, Throwable cause) {
        super(message, cause);
    }

}
