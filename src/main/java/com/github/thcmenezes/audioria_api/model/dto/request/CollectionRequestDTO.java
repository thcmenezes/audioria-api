package com.github.thcmenezes.audioria_api.model.dto.request;

import java.util.Optional;

public record CollectionRequestDTO(
        String name,
        Optional<String> description
    ) { }
