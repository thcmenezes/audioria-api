package com.github.thcmenezes.audioria_api.model.mapper;

import com.github.thcmenezes.audioria_api.model.dto.ArtistDTO;
import com.github.thcmenezes.audioria_api.model.dto.request.ArtistRequestDTO;
import com.github.thcmenezes.audioria_api.model.dto.response.ArtistResponseDTO;
import com.github.thcmenezes.audioria_api.model.entity.Artist;

import java.util.Optional;

public class ArtistMapper {
    public static Artist toEntity(ArtistDTO dto) {
        if (dto == null) return null;

        Artist artist = new Artist();
        artist.setName(dto.name());

        return artist;
    }

    public static ArtistDTO toDTO(Artist artist) {
        if (artist == null) return null;

        return new ArtistDTO(Optional.ofNullable(artist.getId()), artist.getName());
    }

    public static ArtistResponseDTO toResponseDTO(Artist artist) {
        if (artist == null) return null;

        return new ArtistResponseDTO(artist.getId(), artist.getName());
    }

    public static ArtistDTO fromRequestDTO(ArtistRequestDTO request) {
        if (request == null) return null;
        return new ArtistDTO(null, request.name());
    }
}

