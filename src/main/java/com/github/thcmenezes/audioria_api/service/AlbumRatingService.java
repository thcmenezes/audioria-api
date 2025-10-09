package com.github.thcmenezes.audioria_api.service;

import com.github.thcmenezes.audioria_api.model.entity.Album;
import com.github.thcmenezes.audioria_api.model.entity.AlbumRating;
import com.github.thcmenezes.audioria_api.model.entity.AlbumRatingSource;
import com.github.thcmenezes.audioria_api.repository.AlbumRatingRepository;
import com.github.thcmenezes.audioria_api.repository.AlbumRatingSourceRepository;
import com.github.thcmenezes.audioria_api.repository.AlbumRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AlbumRatingService {
    private final AlbumRatingRepository albumRatingRepository;
    private final AlbumRepository albumRepository;
    private final AlbumRatingSourceRepository sourceRepository;

    public boolean addRating(UUID albumId, UUID sourceId) {
        Optional<Album> album = albumRepository.findById(albumId);
        Optional<AlbumRatingSource> source = sourceRepository.findById(sourceId);

        if (album.isEmpty() || source.isEmpty()) return false;

        if (albumRatingRepository.existsByAlbumAndRatingSource(album.get(), source.get())) {
            return false;
        }

        AlbumRating rating = new AlbumRating();
        rating.setAlbum(album.get());
        rating.setRatingSource(source.get());
        rating.setCreatedAt(LocalDateTime.now());

        albumRatingRepository.save(rating);
        return true;
    }

    public boolean removeRating(UUID albumId, UUID sourceId) {
        Optional<AlbumRating> albumRating = albumRatingRepository.findByAlbumIdAndRatingSourceId(albumId, sourceId);

        if (albumRating.isEmpty()) {
            return false;
        }

        albumRatingRepository.delete(albumRating.get());
        return true;
    }

}
