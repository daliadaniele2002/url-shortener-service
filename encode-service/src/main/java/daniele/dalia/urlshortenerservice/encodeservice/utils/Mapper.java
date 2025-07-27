package daniele.dalia.urlshortenerservice.encodeservice.utils;

import daniele.dalia.urlshortenerservice.encodeservice.dto.ShortenRequest;
import daniele.dalia.urlshortenerservice.encodeservice.dto.ShortenResponse;
import daniele.dalia.urlshortenerservice.encodeservice.entity.ShortUrlEntity;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;

public class Mapper {

    public static ShortenResponse toResponseDto(String baseUrl, ShortUrlEntity entity) {
        return new ShortenResponse(
                baseUrl + "/" + entity.getShortCode(),
                entity.getShortCode(),
                entity.getExpireAt().toString()
        );
    }

    public static ShortUrlEntity toEntity(ShortenRequest request, String shortUrl, Instant now) {
        var entity = new ShortUrlEntity();
        entity.setOriginalUrl(request.originalUrl());
        entity.setShortCode(shortUrl);
        entity.setCreatedAt(Timestamp.from(now));
        if (request.expiresInDays() != null) {
            entity.setExpireAt(Timestamp.from(now.plus(Duration.ofDays(request.expiresInDays()))));
        } else {
            entity.setExpireAt(Timestamp.from(now.plus(Duration.ofDays(365))));
        }

        return entity;
    }

}
