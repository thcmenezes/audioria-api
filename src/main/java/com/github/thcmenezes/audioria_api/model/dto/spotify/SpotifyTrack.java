package com.github.thcmenezes.audioria_api.model.dto.spotify;

import java.util.List;

public record SpotifyTrack(
        String id,
        String name,
        List<SpotifyArtist> artists,
        SpotifyAlbum album
) {}
