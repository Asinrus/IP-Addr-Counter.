package org.github.ipfilereader.exception;

public class InterruptedReadOperation extends RuntimeException {

    public InterruptedReadOperation(Throwable cause) {
        super(cause);
    }

    public InterruptedReadOperation(String message) {
        super(message);
    }
}