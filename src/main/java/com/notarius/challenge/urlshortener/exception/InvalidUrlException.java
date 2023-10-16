package com.notarius.challenge.urlshortener.exception;

public class NotUrlException extends RuntimeException{
    public NotUrlException(String invalidUrl) {
        super(invalidUrl + "is not a valid Url");
    }
}
