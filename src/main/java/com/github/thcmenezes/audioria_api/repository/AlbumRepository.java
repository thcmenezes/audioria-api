package com.github.thcmenezes.audioria_api.repository;

import com.github.thcmenezes.audioria_api.model.entity.Album;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AlbumRepository extends JpaRepository<Album, UUID> {
    List<Album> findByArtistNameIgnoreCase(String artistName);

    List<Album> findByArtistId(UUID artistId);

    boolean existsByTitleIgnoreCaseAndArtistId(String title, UUID artistId);

    boolean existsByTitleIgnoreCaseAndArtistIdAndIdNot(String title, UUID artistId, UUID id);

    Optional<Album> findByTitleIgnoreCaseAndArtistId(String title, UUID artistId);

}
