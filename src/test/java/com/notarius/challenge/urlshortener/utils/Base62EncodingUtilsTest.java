package com.notarius.challenge.urlshortener.utils;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(MockitoJUnitRunner.class)
class Base62EncodingUtilsTest {

    @Test
    void encodeTest() {
        assertEquals("q8Nddv", Base62EncodingUtils.encode(956547000L));
    }

    @Test
    void encode_WhenInputIs0_Test() {
        assertEquals("F", Base62EncodingUtils.encode(0L));
    }

    @Test
    void decodeTest() {
        assertEquals(19158597494L, Base62EncodingUtils.decode("Whd4DF"));
    }
}