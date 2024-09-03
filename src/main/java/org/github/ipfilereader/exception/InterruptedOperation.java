package org.github.ipfilereader.exception;

public class InterruptedOperation extends RuntimeException {

    public InterruptedOperation(Throwable cause) {
        super(cause);
    }

    public InterruptedOperation(String message) {
        super(message);
    }

    public InterruptedOperation(String message, Throwable cause) {
        super(message, cause);
    }
}
