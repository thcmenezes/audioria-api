package com.github.thcmenezes.audioria_api.model.dto.response;

import java.util.UUID;

public record AlbumRatingSourceResponseDTO(
        UUID id,
        String name,
        Integer scoreValue
) {
}
