package daniele.dalia.urlshortenerservice.encodeservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import daniele.dalia.urlshortenerservice.encodeservice.dto.ShortenRequest;
import daniele.dalia.urlshortenerservice.encodeservice.dto.ShortenResponse;
import daniele.dalia.urlshortenerservice.encodeservice.repo.ShortUrlRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UrlShortenerControllerTest extends BaseControllerTest {

    @Value("${base-url}")
    private String baseUrl;

    @Autowired
    private ShortUrlRepository repository;

    @Test
    void shorten_returns202() throws Exception {
        var mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        var expiresInDays = 1;
        var now = LocalDateTime.now();

        var request = new ShortenRequest("http://some/long/url/toMakeShort", expiresInDays);

        var result = mockMvc.perform(post("/shorten")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsBytes(request)))
                .andExpect(status().isOk())
                .andReturn();

        var responseBody = result.getResponse().getContentAsString();

        var actual = mapper.readValue(responseBody, ShortenResponse.class);

        assertNotNull(actual.shortUrl());
        assertNotNull(actual.shortCode());
        assertEquals(baseUrl + "/" + actual.shortCode(), actual.shortUrl());


        assertNotNull(actual.expireAt());

        var expireAt = actual.expireAt();
        long daysBetween = ChronoUnit.DAYS.between(now, expireAt);

        assertTrue(daysBetween == expiresInDays || daysBetween == 0);

        var entity = repository.findByShortCode((actual.shortCode()));
        assertTrue(entity.isPresent());
        assertEquals(request.originalUrl(), entity.get().getOriginalUrl());
    }

    @Test
    void shorten_returns202_and_default_expires_at() throws Exception {
        var mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        var expiresInDays = 365;
        var now = LocalDateTime.now();

        var request = new ShortenRequest("http://some/long/url/toMakeShort", null);

        var result = mockMvc.perform(post("/shorten")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsBytes(request)))
                .andExpect(status().isOk())
                .andReturn();

        var responseBody = result.getResponse().getContentAsString();

        var actual = mapper.readValue(responseBody, ShortenResponse.class);

        assertNotNull(actual.shortUrl());
        assertNotNull(actual.shortCode());
        assertEquals(baseUrl + "/" + actual.shortCode(), actual.shortUrl());


        assertNotNull(actual.expireAt());

        var expireAt = actual.expireAt();
        long daysBetween = ChronoUnit.DAYS.between(now, expireAt);

        assertTrue(daysBetween == expiresInDays || daysBetween == 0);

        var entity = repository.findByShortCode((actual.shortCode()));
        assertTrue(entity.isPresent());
        assertEquals(request.originalUrl(), entity.get().getOriginalUrl());
    }
}