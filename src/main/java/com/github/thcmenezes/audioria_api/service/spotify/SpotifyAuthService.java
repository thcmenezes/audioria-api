package com.github.thcmenezes.audioria_api.service.spotify;

import com.github.thcmenezes.audioria_api.model.dto.spotify.SpotifyTokenResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Base64;

@Service
public class SpotifyAuthService {

    private final SpotifyProperties props;
    private final WebClient webClient = WebClient.builder()
            .baseUrl("https://accounts.spotify.com")
            .build();

    public SpotifyAuthService(SpotifyProperties props) {
        this.props = props;
    }

    public void printCredenciais() {
        System.out.println("Client ID: " + props.getClientId());
        System.out.println("Base URL: " + props.getBaseUrl());
    }

    public String getAccessToken() {
        String credentials = props.getClientId() + ":" + props.getClientSecret();
        String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes());

        SpotifyTokenResponse response = webClient.post()
                .uri("/api/token")
                .header("Authorization", "Basic " + encodedCredentials)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .bodyValue("grant_type=client_credentials")
                .retrieve()
                .bodyToMono(SpotifyTokenResponse.class)
                .blockOptional()
                .orElseThrow(() -> new RuntimeException("Failed to obtain Spotify token"));

        return response.getAccess_token();
    }
}
