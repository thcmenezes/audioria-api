package com.github.thcmenezes.audioria_api.repository;

import com.github.thcmenezes.audioria_api.model.entity.Artist;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ArtistRepository extends JpaRepository<Artist, UUID> {
    default Artist getByIdOrThrow(UUID id) {
        return findById(id).orElseThrow(() ->
                new EntityNotFoundException("Artist not found with ID: " + id));
    }

    boolean existsByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCaseAndIdNot(String name, UUID id);

    Optional<Artist> findByNameIgnoreCase(String name);
}
