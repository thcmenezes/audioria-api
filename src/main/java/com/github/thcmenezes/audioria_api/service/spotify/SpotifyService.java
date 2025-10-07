package com.github.thcmenezes.audioria_api.service.spotify;

import com.github.thcmenezes.audioria_api.model.dto.spotify.*;
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
    private final SpotifyPersistenceService persistenceService;

    public void importFromPlaylist(String playlistUrl) {
        String playlistId = SpotifyUtils.extractPlaylistId(playlistUrl);
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
                        Integer releaseYear = Optional.ofNullable(album)
                                .map(a -> SpotifyUtils.extractAlbumReleaseYear(a.releaseDate()))
                                .map(Integer::parseInt)
                                .orElse(null);

                        if (processedAlbums.add(albumName)) {
                            System.out.printf("💿 Gravando álbum: %s (%d) — %s%n", albumName, releaseYear, artistName);
                            persistenceService.saveArtistAndAlbum(
                                    artistName,
                                    albumName,
                                    releaseYear
                            );
                        }
                    });

            offset += limit;
        }
    }
}
