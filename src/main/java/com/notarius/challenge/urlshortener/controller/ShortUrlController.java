package com.notarius.challenge.urlshortener.controller;

import com.notarius.challenge.urlshortener.service.ShorteningService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class ShortUrlController {
    private final ShorteningService shorteningService;

    public ShortUrlController(ShorteningService shorteningService) {
        this.shorteningService = shorteningService;
    }

    //    @ApiOperation(value = "Convert new url", notes = "Converts long url to short url")
    @GetMapping("/shorten-url")
    public String convertToShortUrl(@RequestParam String longUrl) {
        return shorteningService.convertToShortUrl(longUrl);
    }

    @GetMapping(value = "/original-url")
    public ResponseEntity<String> getOriginalUrl(@RequestParam String shortUrl) {
        String longUrl = shorteningService.getOriginalUrl(shortUrl);
        return new ResponseEntity<>(longUrl, HttpStatus.OK);
    }
}
