package com.maculuve.exceptions.handler;

import java.util.Date;

import com.maculuve.exceptions.RequiredObjectIsNullException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.maculuve.exceptions.BadRequestException;
import com.maculuve.exceptions.ExceptionResponse;
import com.maculuve.exceptions.FileNotFoundException;
import com.maculuve.exceptions.FileStorageException;
import com.maculuve.exceptions.InvalidJwtAuthenticationException;
import com.maculuve.exceptions.ResourceNotFoundException;

@ControllerAdvice
@RestController
public class CustomizeResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

        @ExceptionHandler(Exception.class)
        public final ResponseEntity<ExceptionResponse> handlerAllExceptions(Exception ex, WebRequest webRequest) {
                ExceptionResponse exceptionResponse = new ExceptionResponse(
                                new Date(),
                                ex.getMessage(),
                                webRequest.getDescription(false));

                return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        @ExceptionHandler(ResourceNotFoundException.class)
        public final ResponseEntity<ExceptionResponse> handlerResourceNotFoundExceptions(Exception ex,
                        WebRequest webRequest) {
                ExceptionResponse exceptionResponse = new ExceptionResponse(
                                new Date(),
                                ex.getMessage(),
                                webRequest.getDescription(false));

                return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
        }

        // @ExceptionHandler(InvalidJwtAuthenticationException.class)
        // public final ResponseEntity<ExceptionResponse> handlerInvalidJwtAuthenticationExceptions(Exception ex,
        //                 WebRequest webRequest) {
        //         ExceptionResponse exceptionResponse = new ExceptionResponse(
        //                         new Date(),
        //                         ex.getMessage(),
        //                         webRequest.getDescription(false));

        //         return new ResponseEntity<>(exceptionResponse, HttpStatus.FORBIDDEN);
        // }

        @ExceptionHandler(RequiredObjectIsNullException.class)
        public final ResponseEntity<ExceptionResponse> handlerRequiredObjectIsNullExceptions(Exception ex,
                        WebRequest webRequest) {
                ExceptionResponse exceptionResponse = new ExceptionResponse(
                                new Date(),
                                ex.getMessage(),
                                webRequest.getDescription(false));

                return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
        }

        @ExceptionHandler(FileNotFoundException.class)
        public final ResponseEntity<ExceptionResponse> handlerFileNotFoundExceptions(Exception ex,
                        WebRequest webRequest) {
                ExceptionResponse exceptionResponse = new ExceptionResponse(
                                new Date(),
                                ex.getMessage(),
                                webRequest.getDescription(false));

                return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
        }

        @ExceptionHandler(FileStorageException.class)
        public final ResponseEntity<ExceptionResponse> handlerFileStorageExceptions(Exception ex,
                        WebRequest webRequest) {
                ExceptionResponse exceptionResponse = new ExceptionResponse(
                                new Date(),
                                ex.getMessage(),
                                webRequest.getDescription(false));

                return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        @ExceptionHandler(BadRequestException.class)
        public final ResponseEntity<ExceptionResponse> handlerBadRequestExceptions(Exception ex,
                        WebRequest webRequest) {
                ExceptionResponse exceptionResponse = new ExceptionResponse(
                                new Date(),
                                ex.getMessage(),
                                webRequest.getDescription(false));

                return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
        }
}
