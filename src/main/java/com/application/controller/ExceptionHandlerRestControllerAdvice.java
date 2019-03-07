package com.application.controller;

import com.application.message.ErrorMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;

@RestControllerAdvice
public class ExceptionHandlerRestControllerAdvice {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandlerRestControllerAdvice.class);

    @ExceptionHandler({Throwable.class})
    public ResponseEntity<ErrorMessage> handleGlobally(Exception e) {
        logger.error("Handled exception: {}", e);

        String reason = e.getMessage();
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ResponseStatus rs = AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class);

        if (rs != null) {
            reason = rs.reason();
            status = rs.code();
        }

        ErrorMessage errorMessage = new ErrorMessage(new Date(), reason);
        return new ResponseEntity<>(errorMessage, status);
    }

}