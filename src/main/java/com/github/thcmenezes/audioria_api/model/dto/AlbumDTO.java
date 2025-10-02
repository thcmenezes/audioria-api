package com.github.thcmenezes.audioria_api.model.dto;

import java.util.Optional;
import java.util.UUID;

public record AlbumDTO(
        Optional<UUID> id,
        String title,
        Optional<String> coverUrl,
        Integer releasedYear,
        UUID artistId
    ) {
}
