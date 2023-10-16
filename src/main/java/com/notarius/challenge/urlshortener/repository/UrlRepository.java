package com.notarius.challenge.urlshortener.repository;

import com.notarius.challenge.urlshortener.model.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UrlRepository extends JpaRepository<Url, Long> {
    Optional<Url> findOneByLongUrl(String longUrl);
}
