package com.github.thcmenezes.audioria_api.model.keys;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Embeddable
public class CollectionAlbumId {
    @Column(name = "collection_id")
    private UUID collectionId;
    @Column(name = "album_id")
    private UUID albumId;

    public CollectionAlbumId() {
    }

    public CollectionAlbumId(UUID collectionId, UUID albumId) {
        this.collectionId = collectionId;
        this.albumId = albumId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CollectionAlbumId that = (CollectionAlbumId) o;
        return Objects.equals(collectionId, that.collectionId) &&
                Objects.equals(albumId, that.albumId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(collectionId, albumId);
    }
}
