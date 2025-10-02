package com.github.thcmenezes.audioria_api.repository;

import com.github.thcmenezes.audioria_api.model.entity.Collection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CollectionRepository extends JpaRepository<Collection, UUID> {
    boolean existsByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCaseAndIdNot(String name, UUID id);
}
