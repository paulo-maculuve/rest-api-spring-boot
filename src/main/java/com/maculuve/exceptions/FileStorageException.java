package com.maculuve.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class FileStorageException extends RuntimeException {

    public FileStorageException(String error) {
        super(error);
    }

    public FileStorageException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
