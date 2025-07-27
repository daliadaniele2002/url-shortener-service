package daniele.dalia.urlshortenerservice.encodeservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record ShortenRequest(
        @NotBlank @Pattern(
                regexp = "^(https?|ftp)://[^\\s/$.?#].[^\\s]*$",
                message = "Invalid URL format"
        ) String originalUrl,
        @Min(1) Integer expiresInDays) {
}
