package com.notarius.challenge.urlshortener.service;

import com.notarius.challenge.urlshortener.exception.InvalidUrlException;
import com.notarius.challenge.urlshortener.exception.UrlNotFoundException;
import com.notarius.challenge.urlshortener.model.Url;
import com.notarius.challenge.urlshortener.repository.UrlRepository;
import com.notarius.challenge.urlshortener.utils.Base62EncodingUtils;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Service
public class ShorteningService {

    private final UrlRepository urlRepository;
    private final String serviceName;

    public ShorteningService(@Value("${spring.application.name}") String serviceName, UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
        this.serviceName = serviceName;
    }

    public String convertToShortUrl(String longUrl) {
        //Decode url in utf-8 first in case url is encoded
        String decodedUrl = URLDecoder.decode(longUrl, StandardCharsets.UTF_8);
        if(!longUrl.equals(decodedUrl)){
            longUrl = decodedUrl;
        }

        StringBuilder shortenUrlBuilder = new StringBuilder();
        //Validate url
        if (!isValidURL(longUrl)) {
            throw new InvalidUrlException(longUrl);
        }

        //If long url is not existing in database, entity will be saved
        Optional<Url> existingLongUrl = urlRepository.findOneByLongUrl(longUrl);
        Url urlEntity;
        if (existingLongUrl.isEmpty()) {
            Url url = new Url();
            url.setLongUrl(longUrl);
            urlEntity = urlRepository.save(url);
        } else {
            //if url exists, just return existing shorten url
            urlEntity = existingLongUrl.get();
        }
        shortenUrlBuilder.append(this.serviceName).append(Base62EncodingUtils.encode(urlEntity.getId()));
        return shortenUrlBuilder.toString();
    }

    public String getOriginalUrl(String shortUrl) {
        String urlBase62Id = shortUrl.substring(serviceName.length());
        long id = Base62EncodingUtils.decode(urlBase62Id);
        Url urlEntity = urlRepository.findById(id)
                .orElseThrow(() -> new UrlNotFoundException(shortUrl));

        return urlEntity.getLongUrl();
    }

    protected boolean isValidURL(String url) {
        String[] schemes = {"http", "https"};
        UrlValidator urlValidator = new UrlValidator(schemes);
        return urlValidator.isValid(url);
    }
}
