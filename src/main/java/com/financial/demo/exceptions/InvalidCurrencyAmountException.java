package com.financial.demo.exceptions;

public class InvalidCurrencyAmountException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public InvalidCurrencyAmountException() {
        super();
    }

    public InvalidCurrencyAmountException(String message) {
        super(message);
    }

    public InvalidCurrencyAmountException(Throwable cause) {
        super(cause);
    }

    public InvalidCurrencyAmountException(String message, Throwable cause) {
        super(message, cause);
    }
}
