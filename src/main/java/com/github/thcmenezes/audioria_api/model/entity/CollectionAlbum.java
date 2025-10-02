package com.github.thcmenezes.audioria_api.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import com.github.thcmenezes.audioria_api.model.keys.CollectionAlbumId;

import java.time.LocalDateTime;

@Entity
@Table(name = "collection_album")
@Getter
@Setter
public class CollectionAlbum {
    @EmbeddedId
    private CollectionAlbumId id;

    @ManyToOne
    @MapsId("collectionId")
    @JoinColumn(name = "collection_id")
    private Collection collection;

    @ManyToOne
    @MapsId("albumId")
    @JoinColumn(name = "album_id")
    private Album album;

    private LocalDateTime addedAt = LocalDateTime.now();

    public CollectionAlbum() { }

    public CollectionAlbum(Collection collection, Album album) {
        if (collection == null || album == null) {
            throw new IllegalArgumentException("Collection and Album can´t be null.");
        }

        this.id = new CollectionAlbumId(collection.getId(), album.getId());
    }
}
