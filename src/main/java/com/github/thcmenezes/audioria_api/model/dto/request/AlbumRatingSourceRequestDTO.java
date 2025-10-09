package com.github.thcmenezes.audioria_api.model.dto.request;

import org.antlr.v4.runtime.misc.NotNull;

public record AlbumRatingSourceRequestDTO(
        String name,
        Integer scoreValue
) {
}
