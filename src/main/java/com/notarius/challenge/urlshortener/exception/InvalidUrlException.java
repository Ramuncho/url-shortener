package com.notarius.challenge.urlshortener.exception;

public class InvalidUrlException extends RuntimeException{
    public InvalidUrlException(String invalidUrl) {
        super(invalidUrl + " is not a valid Url");
    }
}
