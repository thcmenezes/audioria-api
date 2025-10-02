package com.github.thcmenezes.audioria_api.model.dto.response;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public record CollectionResponseDTO(
        UUID id,
        String name,
        Optional<String> description,
        List<AlbumResponseDTO>albums
) { }
