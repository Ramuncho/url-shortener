package com.notarius.challenge.urlshortener.utils;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(MockitoJUnitRunner.class)
class Base62EncodingUtilsTest {

    @Test
    public void encode() {
        assertEquals("q8Nddv", Base62EncodingUtils.encode(956547000L));
    }

    @Test
    void decode() {
        assertEquals(19158597494L, Base62EncodingUtils.decode("Whd4DF"));
    }
}