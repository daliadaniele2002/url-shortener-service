package daniele.dalia.urlshortenerservice.encodeservice.controller;

import daniele.dalia.urlshortenerservice.encodeservice.dto.ShortenRequest;
import daniele.dalia.urlshortenerservice.encodeservice.dto.ShortenResponse;
import daniele.dalia.urlshortenerservice.encodeservice.service.UrlShortenerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shorten")
@RequiredArgsConstructor
public class UrlShortenerController {
    private final UrlShortenerService shortenerService;

    @PostMapping
    public ResponseEntity<ShortenResponse> shorten(@RequestBody @Valid ShortenRequest request) {
        return ResponseEntity.ok(shortenerService.shorten(request));
    }
}
