package daniele.dalia.urlshortenerservice.encodeservice.service;

import daniele.dalia.urlshortenerservice.encodeservice.dto.ShortenRequest;
import daniele.dalia.urlshortenerservice.encodeservice.dto.ShortenResponse;
import daniele.dalia.urlshortenerservice.encodeservice.repo.ShortUrlRepository;
import daniele.dalia.urlshortenerservice.encodeservice.utils.Mapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class UrlShortenerService {
    public static final int CODE_LENGTH = 8;
    public final String baseUrl;
    private final ShortUrlRepository repo;

    public UrlShortenerService(@Value("${baseUrl}") String baseUrl,
                               ShortUrlRepository repo) {
        this.baseUrl = baseUrl;
        this.repo = repo;
    }

    public ShortenResponse shorten(ShortenRequest request) {
        var now = Instant.now();
        var shortUrl = generateShortUrl();

        var entity = Mapper.toEntity(request, shortUrl, now);
        repo.save(entity);

        return Mapper.toResponseDto(baseUrl, entity);
    }

    private String generateShortUrl() {
        return UUID.randomUUID().toString().substring(0, CODE_LENGTH);
    }
}
