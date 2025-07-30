package daniele.dalia.urlshortenerservice.decodeservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@EntityScan("daniele.dalia.urlshortenerservice.common.entity")
@ActiveProfiles("test")
class DecodeServiceApplicationTests {

    @Test
    void contextLoads() {
    }

}
