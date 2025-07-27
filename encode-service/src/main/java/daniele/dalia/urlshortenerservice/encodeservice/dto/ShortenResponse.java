package daniele.dalia.urlshortenerservice.encodeservice.dto;

import java.time.LocalDateTime;

public record ShortenResponse(String shortUrl, String shortCode, LocalDateTime expireAt) {
}
