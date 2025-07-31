package daniele.dalia.urlshortenerservice.apigateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayRoutesConfig {
    @Bean
    public RouteLocator customRoutes(RouteLocatorBuilder builder,
                                     @Value("${services.encode}") String encodePath,
                                     @Value("${services.decode}") String decodePath) {
        return builder.routes()
                .route("encode-service", r -> r
                        .path("/encode/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri(encodePath))
                .route("decode-service", r -> r
                        .path("/decode/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri(decodePath))
                .build();
    }
}
