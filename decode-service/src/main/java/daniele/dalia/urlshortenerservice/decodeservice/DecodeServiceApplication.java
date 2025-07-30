package daniele.dalia.urlshortenerservice.decodeservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("daniele.dalia.urlshortenerservice.common.entity")
public class DecodeServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DecodeServiceApplication.class, args);
    }

}
