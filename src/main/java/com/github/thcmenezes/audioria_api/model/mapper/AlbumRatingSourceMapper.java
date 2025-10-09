package com.github.thcmenezes.audioria_api.model.mapper;

import com.github.thcmenezes.audioria_api.model.dto.request.AlbumRatingSourceRequestDTO;
import com.github.thcmenezes.audioria_api.model.dto.response.AlbumRatingSourceResponseDTO;
import com.github.thcmenezes.audioria_api.model.entity.AlbumRatingSource;

public class AlbumRatingSourceMapper {
    public static AlbumRatingSource fromRequestDTO(AlbumRatingSourceRequestDTO dto) {
        AlbumRatingSource source = new AlbumRatingSource();

        source.setName(dto.name());
        source.setScoreValue(dto.scoreValue());

        return source;
    }

    public static AlbumRatingSourceResponseDTO toResponseDTO(AlbumRatingSource source) {
        return new AlbumRatingSourceResponseDTO(
                source.getId(),
                source.getName(),
                source.getScoreValue()
        );
    }
}

