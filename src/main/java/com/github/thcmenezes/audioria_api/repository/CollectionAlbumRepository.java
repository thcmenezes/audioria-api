package com.github.thcmenezes.audioria_api.repository;

import com.github.thcmenezes.audioria_api.model.entity.CollectionAlbum;
import com.github.thcmenezes.audioria_api.model.keys.CollectionAlbumId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CollectionAlbumRepository extends JpaRepository<CollectionAlbum, CollectionAlbumId> {
}
