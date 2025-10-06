package com.github.thcmenezes.audioria_api.service.spotify;

import com.github.thcmenezes.audioria_api.model.dto.spotify.SpotifyAlbum;
import com.github.thcmenezes.audioria_api.model.dto.spotify.SpotifyPlaylistItem;
import com.github.thcmenezes.audioria_api.model.dto.spotify.SpotifyPlaylistResponse;
import com.github.thcmenezes.audioria_api.model.dto.spotify.SpotifyTrack;
import com.github.thcmenezes.audioria_api.repository.AlbumRepository;
import com.github.thcmenezes.audioria_api.repository.ArtistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class SpotifyService {
    private final SpotifyAuthService authService;
    private final ArtistRepository artistRepository;
    private final AlbumRepository albumRepository;

    public void importFromPlaylist(String playlistUrl) {
        String playlistId = extractPlaylistId(playlistUrl);
        String token = authService.getAccessToken();

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        int limit = 100;
        int offset = 0;
        boolean more = true;

        Set<String> processedAlbums = new HashSet<>();
        Set<String> processedArtists = new HashSet<>();

        while (more) {
            String url = String.format(
                    "https://api.spotify.com/v1/playlists/%s/tracks?limit=%d&offset=%d",
                    playlistId, limit, offset
            );

            ResponseEntity<SpotifyPlaylistResponse> response = restTemplate.exchange(
                    url, HttpMethod.GET, entity, SpotifyPlaylistResponse.class
            );

            SpotifyPlaylistResponse playlistResponse = response.getBody();
            if (playlistResponse == null || playlistResponse.items() == null || playlistResponse.items().isEmpty()) {
                more = false;
                continue;
            }

            playlistResponse.items().stream()
                    .map(SpotifyPlaylistItem::track)
                    .filter(Objects::nonNull)
                    .forEach(track -> {
                        String artistName = Optional.ofNullable(track.artists())
                                .filter(list -> !list.isEmpty())
                                .map(list -> list.getFirst().name())
                                .orElse("Unknown");

                        SpotifyAlbum album = track.album();
                        String albumName = album != null ? album.name() : "Unknown";
                        String releaseYear = extractAlbumReleaseYear(album != null ? album.releaseDate() : null);

                        if (processedAlbums.add(albumName)) {
                            System.out.printf("💿 Novo álbum: %s (%s)%n", albumName, releaseYear);
                            // albumRepository.saveIfNotExists(albumName, releaseYear);
                        }

                        if (processedArtists.add(artistName)) {
                            System.out.printf("🎤 Novo artista: %s%n", artistName);
                            // artistRepository.saveIfNotExists(artistName);
                        }
                    });

            offset += limit;
        }
    }

    private String extractPlaylistId(String url) {
        String[] parts = url.split("/");
        String lastPart = parts[parts.length - 1];
        return lastPart.contains("?") ? lastPart.substring(0, lastPart.indexOf("?")) : lastPart;
    }

    private String extractAlbumReleaseYear(String releaseDate) {
        if (releaseDate == null || releaseDate.isBlank()) return "????";
        return releaseDate.length() >= 4 ? releaseDate.substring(0, 4) : releaseDate;
    }

}
