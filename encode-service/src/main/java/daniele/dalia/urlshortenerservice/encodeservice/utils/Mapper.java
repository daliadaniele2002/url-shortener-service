package daniele.dalia.urlshortenerservice.encodeservice.utils;

import daniele.dalia.urlshortenerservice.encodeservice.dto.ShortenRequest;
import daniele.dalia.urlshortenerservice.encodeservice.dto.ShortenResponse;
import daniele.dalia.urlshortenerservice.common.entity.ShortUrlEntity;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

public class Mapper {

    public static ShortenResponse toResponseDto(String baseUrl, ShortUrlEntity entity) {
        return new ShortenResponse(
                baseUrl + "/" + entity.getShortCode(),
                entity.getShortCode(),
                entity.getExpireAt()
        );
    }

    public static ShortUrlEntity toEntity(ShortenRequest request, String shortUrl, Instant now) {
        var entity = new ShortUrlEntity();
        entity.setOriginalUrl(request.originalUrl());
        entity.setShortCode(shortUrl);

        var zoneId = ZoneId.systemDefault();
        entity.setCreatedAt(now.atZone(zoneId).toLocalDateTime());
        entity.setExpireAt(getExpireAt(request, now, zoneId));

        return entity;
    }

    private static LocalDateTime getExpireAt(ShortenRequest request, Instant now, ZoneId zoneId) {
        var amountToAdd = request.expiresInDays() != null ? request.expiresInDays() : 365;
        var instant = now.plus(amountToAdd, ChronoUnit.DAYS);

        return instant.atZone(zoneId).toLocalDateTime();
    }
}
