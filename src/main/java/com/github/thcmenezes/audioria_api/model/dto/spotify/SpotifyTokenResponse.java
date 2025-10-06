package com.github.thcmenezes.audioria_api.model.dto.spotify;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class SpotifyTokenResponse {
    private String access_token;
    private String token_type;
    private int expires_in;
}
