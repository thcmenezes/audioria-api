package com.github.thcmenezes.audioria_api.exception;

import org.springframework.http.HttpStatus;

public class AlbumRatingSourceException extends RuntimeException {
    private final HttpStatus status;

    public AlbumRatingSourceException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public static AlbumRatingSourceException alreadyExists(String sourceName) {
        return new AlbumRatingSourceException(
                "An rating source with the name already exists: " + sourceName,
                HttpStatus.CONFLICT
        );
    }

    public static AlbumRatingSourceException notFound(String sourceName) {
        return new AlbumRatingSourceException(
                "rating source not found: " + sourceName,
                HttpStatus.NOT_FOUND
        );
    }
}
