package daniele.dalia.urlshortenerservice.decodeservice.controller;

import daniele.dalia.urlshortenerservice.decodeservice.dto.RedirectResponse;
import daniele.dalia.urlshortenerservice.decodeservice.service.RedirectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RedirectController {
    private final RedirectService redirectService;

    @GetMapping("/{shortCode}")
    public ResponseEntity<RedirectResponse> resolve(@PathVariable String shortCode) {
        var response = redirectService.resolve(shortCode);
        return ResponseEntity.ok(response);
    }
}
