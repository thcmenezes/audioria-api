package com.github.thcmenezes.audioria_api.model.dto.spotify;

public class SpotifyUtils {

    public static String extractPlaylistId(String url) {
        String[] parts = url.split("/");
        String lastPart = parts[parts.length - 1];
        return lastPart.contains("?") ? lastPart.substring(0, lastPart.indexOf("?")) : lastPart;
    }

    public static String extractAlbumReleaseYear(String releaseDate) {
        if (releaseDate == null || releaseDate.isBlank()) return "????";
        return releaseDate.length() >= 4 ? releaseDate.substring(0, 4) : releaseDate;
    }
}

