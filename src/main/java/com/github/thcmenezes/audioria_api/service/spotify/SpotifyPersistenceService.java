package com.github.thcmenezes.audioria_api.service.spotify;

import com.github.thcmenezes.audioria_api.model.entity.Album;
import com.github.thcmenezes.audioria_api.model.entity.Artist;
import com.github.thcmenezes.audioria_api.repository.AlbumRepository;
import com.github.thcmenezes.audioria_api.repository.ArtistRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SpotifyPersistenceService {
    private final ArtistRepository artistRepository;
    private final AlbumRepository albumRepository;

    @Transactional
    public void saveArtistAndAlbum(String artistName, String albumTitle, Integer releasedYear) {
        Artist artist = artistRepository.findByNameIgnoreCase(artistName)
                .orElseGet(() -> {
                    Artist newArtist = new Artist();
                    newArtist.setName(artistName);
                    return artistRepository.save(newArtist);
                });

        boolean albumExists = albumRepository
                .findByTitleIgnoreCaseAndArtistId(albumTitle, artist.getId())
                .isPresent();

        if (!albumExists) {
            Album album = new Album();
            album.setTitle(albumTitle);
            album.setReleasedYear(releasedYear);
            album.setArtist(artist);
            albumRepository.save(album);
        }
    }
}
