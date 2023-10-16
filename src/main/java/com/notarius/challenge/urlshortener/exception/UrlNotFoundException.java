package com.notarius.challenge.urlshortener.exception;

public class UrlNotFoundException extends RuntimeException {
    public UrlNotFoundException(String notFoundUrl) {
        super(notFoundUrl + " shorten url has not been found");
    }
}
