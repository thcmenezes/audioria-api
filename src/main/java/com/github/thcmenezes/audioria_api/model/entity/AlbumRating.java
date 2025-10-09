package com.github.thcmenezes.audioria_api.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
@Table(
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"album_id", "rating_source_id"})
        }
)
public class AlbumRating {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "album_id", nullable = false)
    private Album album;

    @ManyToOne
    @JoinColumn(name = "rating_source_id", nullable = false)
    private AlbumRatingSource ratingSource;

    @CreatedDate
    private LocalDateTime createdAt;

    public int getScore() {
        return ratingSource.getScoreValue();
    }
}
