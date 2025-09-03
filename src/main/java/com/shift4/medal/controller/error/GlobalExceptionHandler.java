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

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<String> handleThrowable(Throwable t) {
        log.error("an error occurred", t);

        while (t != null) {
            if (t instanceof UnsupportedMedalException) {
                return ResponseEntity.badRequest().body(t.getMessage());
            }

            if (t instanceof MethodArgumentNotValidException manv) {
                String msg = getFirstValidationMessage(manv);
                return ResponseEntity.badRequest().body(msg != null ? msg : "Validation failed");
            }

            if (t instanceof HttpMessageNotReadableException) {
                return ResponseEntity.badRequest().body("JSON parse error");
            }

            t = t.getCause();
        }

        return ResponseEntity.internalServerError().body("Internal error");
    }

    private String getFirstValidationMessage(MethodArgumentNotValidException ex) {
        FieldError fe = ex.getBindingResult().getFieldError(); // first field error (may be null)
        return fe != null ? fe.getDefaultMessage() : null;
    }
}
