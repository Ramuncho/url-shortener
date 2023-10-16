package com.notarius.challenge.urlshortener;

import com.notarius.challenge.urlshortener.service.ShorteningService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UrlShortenerApplicationTest {
    @Autowired
    private ShorteningService shorteningService;

    @Test
    void contextLoads() {
    }
}