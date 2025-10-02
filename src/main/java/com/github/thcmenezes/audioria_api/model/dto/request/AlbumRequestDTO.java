package com.github.thcmenezes.audioria_api.model.dto.request;

import java.util.Optional;
import java.util.UUID;

public record AlbumRequestDTO(
        String title,
        Optional<String> coverUrl,
        Integer releasedYear,
        UUID artistId
    ) { }
