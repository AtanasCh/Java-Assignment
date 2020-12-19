package com.financial.demo.exceptions;

public class InvalidCurrencyTypeException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public InvalidCurrencyTypeException() {
        super();
    }

    public InvalidCurrencyTypeException(String message) {
        super(message);
    }

    public InvalidCurrencyTypeException(Throwable cause) {
        super(cause);
    }

    public InvalidCurrencyTypeException(String message, Throwable cause) {
        super(message, cause);
    }
}
