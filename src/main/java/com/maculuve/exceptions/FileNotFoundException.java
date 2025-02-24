package com.maculuve.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class FileNotFoundException extends RuntimeException {

    public FileNotFoundException(String error) {
        super(error);
    }

    public FileNotFoundException(String error, Throwable throwable) {
        super(error, throwable);
    }

}
