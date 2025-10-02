package com.github.thcmenezes.audioria_api.model.dto.response;

import java.util.Optional;
import java.util.UUID;

public record AlbumResponseDTO(
        UUID id,
        String title,
        Optional<String> coverUrl,
        Integer releasedYear,
        String artistName,
        UUID artistId
) { }
