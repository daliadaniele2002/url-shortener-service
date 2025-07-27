package daniele.dalia.urlshortenerservice.encodeservice.repo;

import daniele.dalia.urlshortenerservice.encodeservice.entity.ShortUrlEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShortUrlRepository extends JpaRepository<ShortUrlEntity,Long> {
}
