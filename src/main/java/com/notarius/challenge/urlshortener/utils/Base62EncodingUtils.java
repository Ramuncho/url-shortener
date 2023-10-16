package com.notarius.challenge.urlshortener.utils;

import org.springframework.stereotype.Component;

@Component
public class Base62EncodingUtils {

    private static String base62Characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int BASE62_LENGTH = base62Characters.length();

    public static String encode(long numInput){
        StringBuilder encodedStringBuilder = new StringBuilder();

        if(numInput == 0) {
            return String.valueOf(base62Characters.charAt(0));
        }

        while (numInput > 0) {
            encodedStringBuilder.insert(0, base62Characters.charAt((int) (numInput % BASE62_LENGTH)));
            numInput = numInput / BASE62_LENGTH;
        }

        return encodedStringBuilder.toString();
    }

    public static long decode(String stringInput) {
        long decoded = 0L;

        //counter is used to avoid reversing input string
        for (int i = 0; i < stringInput.length(); i++) {
            decoded = decoded * BASE62_LENGTH + base62Characters.indexOf(stringInput.charAt(i));
        }
        return decoded;
    }
}
