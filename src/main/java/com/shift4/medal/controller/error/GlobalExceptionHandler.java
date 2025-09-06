package com.shift4.medal.controller.error;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.shift4.medal.exception.UnsupportedMedalException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        log.error("an error occurred", e);

        Throwable t = e;

        while (t != null) {

            if (t instanceof MethodArgumentNotValidException ex) {
                String msg = getFirstValidationMessage(ex);
                return ResponseEntity.badRequest().body(msg != null ? msg : "Validation failed");
            }

            if (t instanceof HttpMessageNotReadableException ex) {

                return handleHttpMessageNotReadableException(ex);
            }

            if (t instanceof UnsupportedMedalException) {
                return ResponseEntity.badRequest().body(t.getMessage());
            }

            t = t.getCause();
        }

        return ResponseEntity.internalServerError().body("Internal error");
    }

    private String getFirstValidationMessage(MethodArgumentNotValidException e) {
        FieldError fe = e.getBindingResult().getFieldError(); // first field error (may be null)
        return fe != null ? fe.getDefaultMessage() : null;
    }

    private ResponseEntity<String> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {

        Throwable t = e;
        
        while (t != null) { // check if the cause is because of a project custom exception that was thrown.

            if (t instanceof UnsupportedMedalException) {
                return ResponseEntity.badRequest().body(t.getMessage());
            }

            t = t.getCause();
        }

        return ResponseEntity.badRequest().body("JSON parse error");
    }
}
