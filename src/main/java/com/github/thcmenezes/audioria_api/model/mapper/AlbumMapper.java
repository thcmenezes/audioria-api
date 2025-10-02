package com.github.thcmenezes.audioria_api.model.mapper;

import com.github.thcmenezes.audioria_api.model.dto.AlbumDTO;
import com.github.thcmenezes.audioria_api.model.dto.request.AlbumRequestDTO;
import com.github.thcmenezes.audioria_api.model.dto.response.AlbumResponseDTO;
import com.github.thcmenezes.audioria_api.model.entity.Album;
import com.github.thcmenezes.audioria_api.model.entity.Artist;

import java.util.Optional;

public class AlbumMapper {
    public static Album toEntity(AlbumDTO dto, Artist artist) {
        if (dto == null || artist == null) {
            return null;
        }

        Album album = new Album();
        album.setTitle(dto.title());
        album.setCoverUrl(dto.coverUrl().orElse(null));
        album.setReleasedYear(dto.releasedYear());
        album.setArtist(artist);

        return album;
    }

    public static AlbumDTO toDTO(Album album) {
        if (album == null) {
            return null;
        }

        return new AlbumDTO(
                Optional.ofNullable(album.getId()),
                album.getTitle(),
                Optional.ofNullable(album.getCoverUrl()),
                album.getReleasedYear(),
                album.getArtist() != null ? album.getArtist().getId() : null
        );
    }

    public static AlbumResponseDTO toResponseDTO(Album album) {
        if (album == null) {
            return null;
        }

        return new AlbumResponseDTO(
               album.getId(),
                album.getTitle(),
                Optional.ofNullable(album.getCoverUrl()),
                album.getReleasedYear(),
                album.getArtist() != null ? album.getArtist().getName() : null,
                album.getArtist() != null ? album.getArtist().getId() : null
        );
    }

    public static AlbumDTO fromRequestDTO(AlbumRequestDTO request) {
        if (request == null) return null;

        return new AlbumDTO(
                Optional.empty(),
                request.title(),
                request.coverUrl(),
                request.releasedYear(),
                request.artistId()
        );
    }
}
