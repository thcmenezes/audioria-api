package com.github.thcmenezes.audioria_api.controller;

import com.github.thcmenezes.audioria_api.service.spotify.SpotifyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/spotify")
public class SpotifyController {
    private final SpotifyService spotifyService;

    @PostMapping("/import_from_playlist")
    public ResponseEntity<Map<String, String>> importFromPlaylist(@RequestParam("playlist_url") String playlistUrl) {
        spotifyService.importFromPlaylist(playlistUrl);

        return ResponseEntity.ok(Map.of("message", "Albums have been successfully imported"));
    }
}
