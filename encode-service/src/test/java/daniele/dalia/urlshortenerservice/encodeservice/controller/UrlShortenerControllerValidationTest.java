package daniele.dalia.urlshortenerservice.encodeservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import daniele.dalia.urlshortenerservice.encodeservice.dto.ShortenRequest;
import daniele.dalia.urlshortenerservice.encodeservice.config.GlobalExceptionHandler;
import daniele.dalia.urlshortenerservice.encodeservice.service.UrlShortenerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UrlShortenerController.class)
@Import({GlobalExceptionHandler.class})
public class UrlShortenerControllerValidationTest {
    @Autowired
    protected MockMvc mockMvc;

    @MockitoBean
    private UrlShortenerService service;

    @Test
    void should_200() throws Exception {
        var mapper = new ObjectMapper();
        var request = new ShortenRequest("http://some/url", 3);

        when(service.shorten(request)).thenReturn(mock());

        mockMvc.perform(post("/shorten")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsBytes(request)))
                .andExpect(status().isOk());
    }

    @Test
    void should_200_null_expires_at() throws Exception {
        var mapper = new ObjectMapper();
        var request = new ShortenRequest("http://some/url", null);

        when(service.shorten(request)).thenReturn(mock());

        mockMvc.perform(post("/shorten")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsBytes(request)))
                .andExpect(status().isOk());
    }

    @Test
    void should_400_wrong_url() throws Exception {
        var mapper = new ObjectMapper();
        var invalidRequest = new ShortenRequest("wrond", 3);

        mockMvc.perform(post("/shorten")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsBytes(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void should_400_empty_url() throws Exception {
        var mapper = new ObjectMapper();
        var invalidRequest = new ShortenRequest("", 3);

        mockMvc.perform(post("/shorten")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsBytes(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void should_400_blank_url() throws Exception {
        var mapper = new ObjectMapper();
        var invalidRequest = new ShortenRequest(" ", 3);

        mockMvc.perform(post("/shorten")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsBytes(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void should_400_null_url() throws Exception {
        var mapper = new ObjectMapper();
        var invalidRequest = new ShortenRequest(null, 3);

        mockMvc.perform(post("/shorten")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsBytes(invalidRequest)))
                .andExpect(status().isBadRequest());
    }
}
