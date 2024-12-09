package com.linktic.app.exception;

import org.apache.commons.lang3.exception.ExceptionUtils;

public class ExportFileError extends RuntimeException{

    public ExportFileError(String message) {
        super("Error al generar archivo : "+ message);
    }

    public ExportFileError(Throwable cause) {
        super(ExceptionUtils.getRootCause(cause).getMessage());
    }
}
