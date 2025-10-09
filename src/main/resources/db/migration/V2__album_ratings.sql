
CREATE TABLE album_rating_sources (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    score_value INTEGER NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE album_ratings (
    id UUID PRIMARY KEY,
    album_id UUID NOT NULL,
    rating_source_id UUID NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_album_rating_album FOREIGN KEY (album_id) REFERENCES album(id) ON DELETE CASCADE,
    CONSTRAINT fk_album_rating_source FOREIGN KEY (rating_source_id) REFERENCES album_rating_sources(id) ON DELETE CASCADE,
    CONSTRAINT uc_album_source UNIQUE (album_id, rating_source_id)
);
