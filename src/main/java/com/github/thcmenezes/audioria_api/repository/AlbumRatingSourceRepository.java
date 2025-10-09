package com.github.thcmenezes.audioria_api.repository;

import com.github.thcmenezes.audioria_api.model.entity.AlbumRatingSource;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AlbumRatingSourceRepository extends JpaRepository<AlbumRatingSource, UUID> {
    boolean existsByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCaseAndIdNot(String name, UUID id);
}
