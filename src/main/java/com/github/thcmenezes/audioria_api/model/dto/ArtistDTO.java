package com.github.thcmenezes.audioria_api.model.dto;

import java.util.Optional;
import java.util.UUID;

public record ArtistDTO(Optional<UUID> id, String name) {
}
