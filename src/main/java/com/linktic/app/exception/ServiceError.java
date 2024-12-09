package com.linktic.app.exception;

import org.apache.commons.lang3.exception.ExceptionUtils;

public class ServiceError extends RuntimeException{
    public ServiceError(String message) {
        super(message);
    }

    public ServiceError(Throwable cause) {
        super(ExceptionUtils.getRootCause(cause).getMessage());
    }
}
