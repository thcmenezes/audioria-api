package com.github.thcmenezes.audioria_api.exception;

import org.springframework.http.HttpStatus;

public class CollectionException extends RuntimeException {

    private final HttpStatus status;

    public CollectionException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public static CollectionException alreadyExists(String collectionName) {
        return new CollectionException(
                "A collection with the name already exists: " + collectionName,
                HttpStatus.CONFLICT
        );
    }

    public static CollectionException notFound(String collectionName) {
        return new CollectionException(
                "Collection not found: " + collectionName,
                HttpStatus.NOT_FOUND
        );
    }
}
