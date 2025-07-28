package daniele.dalia.urlshortenerservice.encodeservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("daniele.dalia.urlshortenerservice.common.entity")
public class EncodeServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EncodeServiceApplication.class, args);
    }

}
