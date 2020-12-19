package com.financial.demo.exceptions;

public class MissingInputParametersException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public MissingInputParametersException() {
        super();
    }

    public MissingInputParametersException(String message) {
        super(message);
    }

    public MissingInputParametersException(Throwable cause) {
        super(cause);
    }

    public MissingInputParametersException(String message, Throwable cause) {
        super(message, cause);
    }
}
