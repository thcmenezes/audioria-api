package com.github.thcmenezes.audioria_api.model.dto.spotify;

import java.util.List;

public record SpotifyPlaylistResponse(List<SpotifyPlaylistItem> items) {
}
