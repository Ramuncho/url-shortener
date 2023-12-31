package com.notarius.challenge.urlshortener.advice;

import com.notarius.challenge.urlshortener.exception.InvalidUrlException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class InvalidUrlAdvice {
    @ResponseBody
    @ExceptionHandler(InvalidUrlException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String employeeNotFoundHandler(InvalidUrlException ex) {
        return ex.getMessage();
    }
}
