package com.github.thcmenezes.audioria_api.service.spotify;

import com.github.thcmenezes.audioria_api.repository.AlbumRepository;
import com.github.thcmenezes.audioria_api.repository.ArtistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SpotifyService {
    private final SpotifyAuthService authService;
    private final ArtistRepository artistRepository;
    private final AlbumRepository albumRepository;

    public void importFromPlaylist(String playlistUrl) {
        String playlistId = extractPlaylistId(playlistUrl);
        String token = authService.getAccessToken();

        // DO THE REST
    }

    private String extractPlaylistId(String url) {
        String[] parts = url.split("/");
        String lastPart = parts[parts.length - 1];
        return lastPart.contains("?") ? lastPart.substring(0, lastPart.indexOf("?")) : lastPart;
    }
}
