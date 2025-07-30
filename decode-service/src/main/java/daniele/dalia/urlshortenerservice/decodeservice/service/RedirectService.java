package daniele.dalia.urlshortenerservice.decodeservice.service;

import daniele.dalia.urlshortenerservice.common.entity.ShortUrlEntity;
import daniele.dalia.urlshortenerservice.decodeservice.dto.RedirectResponse;
import daniele.dalia.urlshortenerservice.decodeservice.exception.ExpiredException;
import daniele.dalia.urlshortenerservice.decodeservice.exception.NotFoundException;
import daniele.dalia.urlshortenerservice.decodeservice.repo.ShortUrlRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

@Service
public class RedirectService {
    private final ShortUrlRepository shortUrlRepository;

    public RedirectService(ShortUrlRepository shortUrlRepository) {
        this.shortUrlRepository = shortUrlRepository;
    }

    public RedirectResponse resolve(String shortCode) {
        Optional<ShortUrlEntity> optionalEntity = shortUrlRepository.findByShortCode(shortCode);

        if (optionalEntity.isEmpty()) throw new NotFoundException("Unable to resolve shortCode: " + shortCode);

        var entity = optionalEntity.get();

        if (entity.getExpireAt().isBefore(getNow()))
            throw new ExpiredException("Url with code " + shortCode + " expired");

        return new RedirectResponse(entity.getOriginalUrl());
    }

    private LocalDateTime getNow() {
        var now = Instant.now();
        var zoneId = ZoneId.systemDefault();

        return now.atZone(zoneId).toLocalDateTime();
    }
}
