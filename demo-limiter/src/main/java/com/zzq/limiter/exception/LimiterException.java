package com.zzq.limiter.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class LimiterException extends RuntimeException {

    @Getter
    private final HttpStatus status;

    public LimiterException(HttpStatus status, String message, Throwable cause) {
        super(message, cause);
        this.status = status;
    }

    public LimiterException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public void throwIfNot(Boolean b) {
        if (!Boolean.TRUE.equals(b))
            throw this;
    }

    public void throwIf(Boolean b) {
        if (Boolean.TRUE.equals(b))
            throw this;
    }

    public void throwIt() {
        this.throwIfNot(false);
    }
}
