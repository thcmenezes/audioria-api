package com.github.thcmenezes.audioria_api.service.spotify;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "spotify")
public class SpotifyProperties {
    private String clientId;
    private String clientSecret;
    private String baseUrl;
}
