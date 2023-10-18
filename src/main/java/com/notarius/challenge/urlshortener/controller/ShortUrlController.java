package com.notarius.challenge.urlshortener.controller;

import com.notarius.challenge.urlshortener.service.ShorteningService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Convert a valid url to a short url",
            description = "This method returns a shorten url if url is valid")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Url has been successfully shorten"),
            @ApiResponse(responseCode = "400", description = "Url is invalid")
    })
    public String convertToShortUrl(@Parameter(name = "longUrl",
            description = "Long url to short",
            example = "http://www.example.com/foo/bar/test",
            required = true) @RequestParam String longUrl) {
        return shorteningService.convertToShortUrl(longUrl);
    }

    @GetMapping(value = "/original-url")
    @ResponseStatus(HttpStatus.FOUND)
    @Operation(summary = "Retrieve original url from a short url",
            description = "This method returns the original long url if the short, passed as parameter, has been saved previously")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "302", description = "Original url has been retrieved"),
            @ApiResponse(responseCode = "404", description = "Original url has not been found")
    })
    public ResponseEntity < String > getOriginalUrl(
            @Parameter(name = "shortUrl",
                    description = "short url for retrieving orginal long url",
                    example = "http://www.shortyurl.com/as5DE2B",
                    required = true)
            @RequestParam String shortUrl) {
        String longUrl = shorteningService.getOriginalUrl(shortUrl);
        return new ResponseEntity < > (longUrl, HttpStatus.FOUND);
    }
}