package daniele.dalia.urlshortenerservice.encodeservice.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "short_urls")
public class ShortUrlEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "original_url")
    private String originalUrl;
    @Column(name = "short_code")
    private String shortCode;
    @Column(name = "created_at")
    private Timestamp createdAt;
    @Column(name = "expire_at")
    private Timestamp expireAt;
}
