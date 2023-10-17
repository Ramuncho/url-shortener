package com.notarius.challenge.urlshortener.controller;

import com.notarius.challenge.urlshortener.service.ShorteningService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.startsWith;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class ShortUrlControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ShorteningService shorteningService;

    private static final String API_PATH = "/api/v1";

    @Test
    void convertValidDecodedLongUrlReturnStatusOk() throws Exception {
        String decodedLongUrl = "https://www.example.com/foo";

        mockMvc.perform(MockMvcRequestBuilders.get(API_PATH + "/shorten-url").param("longUrl", decodedLongUrl))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(startsWith("http://www.shortyUrl.com/")));
    }

    @Test
    void convertValidEncodedLongUrlReturnStatusOk() throws Exception {
        String encodedLongUrl = "https%3A%2F%2Fwww.example.com%2Ffoo";

        mockMvc.perform(MockMvcRequestBuilders.get(API_PATH + "/shorten-url").param("longUrl", encodedLongUrl))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(startsWith("http://www.shortyUrl.com/")));
    }

    @Test
    void shouldNotInsertFullUrlIfAlreadyExists() throws Exception {
        String decodedLongUrl = "https://www.example.com/foo";
        String encodedLongUrl = "https%3A%2F%2Fwww.example.com%2Ffoo";
        String otherLongUrl = "https://www.example.com/bar";

        String shortUrlFromDecodedUrl = mockMvc.perform(MockMvcRequestBuilders.get(API_PATH + "/shorten-url").param("longUrl", decodedLongUrl))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(startsWith("http://www.shortyUrl.com/")))
                .andReturn().getResponse().getContentAsString();

        String shortUrlFromEncodedUrl = mockMvc.perform(MockMvcRequestBuilders.get(API_PATH + "/shorten-url").param("longUrl", encodedLongUrl))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(startsWith("http://www.shortyUrl.com/")))
                .andReturn().getResponse().getContentAsString();

        String otherShortUrl = mockMvc.perform(MockMvcRequestBuilders.get(API_PATH + "/shorten-url").param("longUrl", otherLongUrl))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(startsWith("http://www.shortyUrl.com/")))
                .andReturn().getResponse().getContentAsString();

        Assert.assertEquals(shortUrlFromDecodedUrl, shortUrlFromEncodedUrl);
        Assert.assertNotEquals(shortUrlFromDecodedUrl, otherShortUrl);
    }

    @Test
    void getOriginalUrl() throws Exception {
        String initialLongUrl = "https://www.example.com/foo";
        String shortUrl = mockMvc.perform(MockMvcRequestBuilders.get(API_PATH + "/shorten-url").param("longUrl", initialLongUrl))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(startsWith("http://www.shortyUrl.com/")))
                .andReturn().getResponse().getContentAsString();

        String originalUrl = mockMvc.perform(MockMvcRequestBuilders.get(API_PATH + "/original-url").param("shortUrl", shortUrl))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        Assert.assertEquals(initialLongUrl, originalUrl);
    }
}