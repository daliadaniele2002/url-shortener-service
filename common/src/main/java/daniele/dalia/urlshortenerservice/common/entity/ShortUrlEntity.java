package daniele.dalia.urlshortenerservice.common.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
        name = "short_urls",
        uniqueConstraints = {
                @UniqueConstraint(name = ShortUrlEntity.UK_SHORT_CODE, columnNames = {"short_code"})
        }
)
public class ShortUrlEntity {
    public static final String UK_SHORT_CODE = "uk_short_code";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "original_url", nullable = false)
    private String originalUrl;
    @Column(name = "short_code", unique = true, nullable = false)
    private String shortCode;
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    @Column(name = "expire_at", nullable = false)
    private LocalDateTime expireAt;
}
