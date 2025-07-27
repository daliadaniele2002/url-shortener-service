package daniele.dalia.urlshortenerservice.encodeservice.service;

import daniele.dalia.urlshortenerservice.encodeservice.dto.ShortenRequest;
import daniele.dalia.urlshortenerservice.encodeservice.dto.ShortenResponse;
import daniele.dalia.urlshortenerservice.encodeservice.entity.ShortUrlEntity;
import daniele.dalia.urlshortenerservice.encodeservice.repo.ShortUrlRepository;
import daniele.dalia.urlshortenerservice.encodeservice.utils.Mapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

import static daniele.dalia.urlshortenerservice.encodeservice.entity.ShortUrlEntity.UK_SHORT_CODE;

@Service
public class UrlShortenerService {
    public static final int CODE_LENGTH = 8;

    public final String baseUrl;
    public final int maxAttempts;
    private final ShortUrlRepository repo;

    public UrlShortenerService(@Value("${base-url}") String baseUrl,
                               @Value("${short-code.max-attempts}") Integer maxAttempts,
                               ShortUrlRepository repo) {
        this.baseUrl = baseUrl;
        this.maxAttempts = maxAttempts;
        this.repo = repo;
    }

    public ShortenResponse shorten(ShortenRequest request) {
        var now = Instant.now();
        var shortUrl = generateShortUrl();

        var entity = Mapper.toEntity(request, shortUrl, now);
        repeatSavingUntilSuccess(entity, 0);

        return Mapper.toResponseDto(baseUrl, entity);
    }

    private void repeatSavingUntilSuccess(ShortUrlEntity entity, int attempts) {
        if (attempts >= maxAttempts)
            throw new IllegalStateException("Failed to generate unique short code after " + maxAttempts + " attempts");

        try {
            repo.save(entity);
        } catch (DataIntegrityViolationException e) {
            if (isShortCodeDuplicateException(e)) {
                var newCode = generateShortUrl();
                entity.setShortCode(newCode);
                repeatSavingUntilSuccess(entity, attempts + 1);
            } else {
                throw e;
            }
        }
    }

    private boolean isShortCodeDuplicateException(DataIntegrityViolationException e) {
        var root = e.getRootCause();
        return root != null &&
                root.getMessage() != null &&
                root.getMessage().contains(UK_SHORT_CODE);
    }

    private String generateShortUrl() {
        return UUID.randomUUID().toString().substring(0, CODE_LENGTH);
    }
}
