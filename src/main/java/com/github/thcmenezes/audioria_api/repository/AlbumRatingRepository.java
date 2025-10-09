package com.github.thcmenezes.audioria_api.repository;

import com.github.thcmenezes.audioria_api.model.entity.Album;
import com.github.thcmenezes.audioria_api.model.entity.AlbumRating;
import com.github.thcmenezes.audioria_api.model.entity.AlbumRatingSource;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AlbumRatingRepository extends JpaRepository<AlbumRating, UUID> {
    boolean existsByAlbumAndRatingSource(Album album, AlbumRatingSource ratingSource);

    List<AlbumRating> findByAlbumId(UUID albumId);

    List<AlbumRating> findByRatingSourceId(UUID ratingSourceId);

    Optional<AlbumRating> findByAlbumIdAndRatingSourceId(UUID albumId, UUID ratingSourceId);

    void deleteByRatingSourceId(UUID ratingSourceId);

}
