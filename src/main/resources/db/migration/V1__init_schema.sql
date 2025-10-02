CREATE TABLE artist (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE album (
    id UUID PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    artist_id UUID NOT NULL,
    is_listened BOOLEAN DEFAULT FALSE,
    cover_url TEXT,
    released_year INTEGER,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_album_artist FOREIGN KEY (artist_id) REFERENCES artist(id) ON DELETE CASCADE
);

CREATE TABLE collection (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE collection_album (
    collection_id UUID  NOT NULL,
    album_id UUID NOT NULL,
    added_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (collection_id, album_id),
    CONSTRAINT fk_collection_album_collection FOREIGN KEY (collection_id) REFERENCES collection(id) ON DELETE CASCADE,
    CONSTRAINT fk_collection_album_album FOREIGN KEY (album_id) REFERENCES album(id) ON DELETE CASCADE
);