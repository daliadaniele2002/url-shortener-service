package daniele.dalia.urlshortenerservice.decodeservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import daniele.dalia.urlshortenerservice.common.entity.ShortUrlEntity;
import daniele.dalia.urlshortenerservice.decodeservice.dto.RedirectResponse;
import daniele.dalia.urlshortenerservice.decodeservice.repo.ShortUrlRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RedirectControllerTest extends BaseControllerTest {

    @Autowired
    private ShortUrlRepository repository;

    @BeforeAll
    void setup() {
        var now = Instant.now();
        var zoneId = ZoneId.systemDefault();
        var expiresAt1 = now.plus(Duration.ofDays(10));
        var expiresAt2 = now.minus(Duration.ofDays(10));

        ShortUrlEntity entity1 = new ShortUrlEntity();
        entity1.setShortCode("code1");
        entity1.setOriginalUrl("https://example.com");
        entity1.setCreatedAt(now.atZone(zoneId).toLocalDateTime());
        entity1.setExpireAt(expiresAt1.atZone(zoneId).toLocalDateTime());

        repository.save(entity1);

        ShortUrlEntity entity2 = new ShortUrlEntity();
        entity2.setShortCode("code2");
        entity2.setOriginalUrl("https://example.com");
        entity2.setCreatedAt(now.atZone(zoneId).toLocalDateTime());
        entity2.setExpireAt(expiresAt2.atZone(zoneId).toLocalDateTime());

        repository.save(entity2);
    }

    @Test
    void resolve_returns200() throws Exception {
        var mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        var result = mockMvc.perform(get("/code1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        var responseBody = result.getResponse().getContentAsString();

        var actual = mapper.readValue(responseBody, RedirectResponse.class);

        assertNotNull(actual);
        assertNotNull(actual.redirectUrl());
        assertEquals("https://example.com", actual.redirectUrl());
    }

    @Test
    void resolve_returns410() throws Exception {
        var mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        mockMvc.perform(get("/code2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isGone());
    }

    @Test
    void resolve_returns404() throws Exception {
        var mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        mockMvc.perform(get("/code3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}