package com.github.thcmenezes.audioria_api.exception;

import org.springframework.http.HttpStatus;

public class AlbumException extends RuntimeException {

    private final HttpStatus status;

    public AlbumException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public static AlbumException alreadyExists(String albumTitle, String artistName) {
        return new AlbumException(
                "Album already exists with title: \"" + albumTitle + "\" for artist: " + artistName,
                HttpStatus.CONFLICT
        );
    }
}
