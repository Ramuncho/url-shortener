package com.notarius.challenge.urlshortener.service;

import com.notarius.challenge.urlshortener.exception.InvalidUrlException;
import com.notarius.challenge.urlshortener.exception.UrlNotFoundException;
import com.notarius.challenge.urlshortener.model.Url;
import com.notarius.challenge.urlshortener.repository.UrlRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class ShorteningServiceTest {
    @Mock
    UrlRepository mockUrlRepository;

    private ShorteningService shorteningService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        shorteningService = new ShorteningService("http://www.shortyUrl.com/", mockUrlRepository);
    }

    private static final String SERVICE_NAME = "http://www.shortyUrl.com/";

    @Test
    void convertToShortUrlTest() {
        String longUrl = "https://vladmihalcea.com/how-to-replace-the-table-identifier-generator-with-either-sequence-or-identity-in-a-portable-way/";
        Url url = new Url();
        url.setLongUrl(longUrl);
        url.setId(956547000L);

        when(mockUrlRepository.save(any(Url.class))).thenReturn(url);
        assertEquals(SERVICE_NAME + "q8Nddv", shorteningService.convertToShortUrl(longUrl));
    }

    @Test
    void getOriginalUrlTest() {
        Url url = new Url();
        url.setLongUrl("https://vladmihalcea.com/how-to-replace-the-table-identifier-generator-with-either-sequence-or-identity-in-a-portable-way/");
        url.setId(956547000L);

        when(mockUrlRepository.findById((long) 956547000L)).thenReturn(java.util.Optional.of(url));
        assertEquals("https://vladmihalcea.com/how-to-replace-the-table-identifier-generator-with-either-sequence-or-identity-in-a-portable-way/", shorteningService.getOriginalUrl(SERVICE_NAME+"q8Nddv"));
    }

    @Test()
    void shouldThrowInvalidUrlExceptionWhenLongUrlIsNotValid() throws InvalidUrlException {
        String invalidUrl = "test://vvvv.blabla.com";
        Throwable exception = assertThrows(InvalidUrlException.class, () ->shorteningService.convertToShortUrl(invalidUrl));
        assertEquals(invalidUrl + " is not a valid Url", exception.getMessage());
    }
    @Test
    void shouldThrowUrlNotFoundExceptionWhenShortUrlDoesNotExist() throws UrlNotFoundException {
        String shortUrl=SERVICE_NAME+"q8Nddv";
        when(mockUrlRepository.findById(956547000L)).thenReturn(java.util.Optional.empty());
        Throwable exception = assertThrows(UrlNotFoundException.class, ()->shorteningService.getOriginalUrl(shortUrl));
        assertEquals(shortUrl + " shorten url has not been found", exception.getMessage());
    }
}