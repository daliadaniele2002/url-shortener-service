package daniele.dalia.urlshortenerservice.decodeservice.repo;

import daniele.dalia.urlshortenerservice.common.entity.ShortUrlEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShortUrlRepository extends JpaRepository<ShortUrlEntity, Long> {
    Optional<ShortUrlEntity> findByShortCode(String shortCode);
}
