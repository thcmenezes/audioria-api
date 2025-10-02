package com.github.thcmenezes.audioria_api.model.mapper;

import com.github.thcmenezes.audioria_api.model.dto.request.CollectionRequestDTO;
import com.github.thcmenezes.audioria_api.model.dto.response.AlbumResponseDTO;
import com.github.thcmenezes.audioria_api.model.dto.response.CollectionResponseDTO;
import com.github.thcmenezes.audioria_api.model.entity.Collection;

import java.util.List;
import java.util.Optional;

public class CollectionMapper {
    public static Collection toEntity(CollectionRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        Collection collection = new Collection();
        collection.setName(dto.name());
        collection.setDescription(dto.description().orElse(null));
        return collection;
    }

    public static CollectionResponseDTO toResponseDTO(Collection collection) {
        if (collection == null) {
            return null;
        }

        List<AlbumResponseDTO> albums = collection.getCollectionAlbums().stream()
                .map(collectionAlbum -> AlbumMapper.toResponseDTO(collectionAlbum.getAlbum()))
                .toList();

        return new CollectionResponseDTO(
                collection.getId(),
                collection.getName(),
                Optional.ofNullable(collection.getDescription()),
                albums
        );
    }
}
