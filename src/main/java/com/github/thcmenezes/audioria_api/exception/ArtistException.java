package com.github.thcmenezes.audioria_api.exception;

import org.springframework.http.HttpStatus;

public class ArtistException extends RuntimeException {

    private final HttpStatus status;

    public ArtistException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public static ArtistException alreadyExists(String artistName) {
        return new ArtistException(
                "An artist with the name already exists: " + artistName,
                HttpStatus.CONFLICT
        );
    }

    public static ArtistException notFound(String artistName) {
        return new ArtistException(
                "Artist not found: " + artistName,
                HttpStatus.NOT_FOUND
        );
    }
}
