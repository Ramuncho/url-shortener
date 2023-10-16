package com.notarius.challenge.urlshortener.advice;

import com.notarius.challenge.urlshortener.exception.UrlNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class UrlNotFoundAdvice {
    @ResponseBody
    @ExceptionHandler(UrlNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String employeeNotFoundHandler(UrlNotFoundException ex) {
        return ex.getMessage();
    }
}
