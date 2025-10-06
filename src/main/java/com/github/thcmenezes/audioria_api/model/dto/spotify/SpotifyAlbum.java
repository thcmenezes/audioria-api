package com.github.thcmenezes.audioria_api.model.dto.spotify;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SpotifyAlbum(
        String name,
        @JsonProperty("release_date") String releaseDate
) {
}
