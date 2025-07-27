package daniele.dalia.urlshortenerservice.encodeservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

    public record ShortenRequest(@NotBlank @URL String originalUrl, @Min(1) Integer expiresInDays) {
}
