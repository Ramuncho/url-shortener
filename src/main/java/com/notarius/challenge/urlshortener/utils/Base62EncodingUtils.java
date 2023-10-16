package com.notarius.challenge.urlshortener.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EncodingService {
    @Value("${urlId.allowedCharacters}")
    private static String base62Characters;
    private static final int BASE62_LENGTH = base62Characters.length();

    public String encode(long numInput){
        StringBuilder encodedStringBuilder = new StringBuilder();

        if(numInput == 0) {
            return String.valueOf(base62Characters.charAt(0));
        }

        while (numInput > 0) {
            encodedStringBuilder.append(base62Characters.charAt((int) (numInput % BASE62_LENGTH)));
            numInput = numInput / BASE62_LENGTH;
        }

        return encodedStringBuilder.toString();
    }

    public long decode(String stringInput) {
        long decoded = 0L;

        //counter is used to avoid reversing input string
        for (int i = 0; i < stringInput.length(); i++) {
            decoded = decoded * BASE62_LENGTH + base62Characters.indexOf(stringInput.charAt(i));
        }
        return decoded;
    }
}
