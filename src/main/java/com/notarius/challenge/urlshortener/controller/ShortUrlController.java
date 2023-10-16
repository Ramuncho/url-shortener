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

//    @ApiOperation(value = "Redirect", notes = "Finds original url from short url and redirects")
    @GetMapping(value = "/original-url")
//    @Cacheable(value = "urls", key = "#shortUrl", sync = true)
    public ResponseEntity<String> getAndRedirect(@RequestParam String shortUrl) {
        String longUrl = shorteningService.getOriginalUrl(shortUrl);
        return new ResponseEntity<>(longUrl, HttpStatus.OK);
    }
}
