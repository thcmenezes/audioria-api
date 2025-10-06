package com.github.thcmenezes.audioria_api.controller;

import com.github.thcmenezes.audioria_api.service.spotify.SpotifyAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/spotify")
public class SpotifyController {
    private final SpotifyAuthService authService;

    @GetMapping("/token")
    public String token() {
        return authService.getAccessToken();
    }
}
