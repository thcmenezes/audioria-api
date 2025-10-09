package com.github.thcmenezes.audioria_api.service;

import com.github.thcmenezes.audioria_api.model.dto.AlbumDTO;
import com.github.thcmenezes.audioria_api.model.entity.Album;
import com.github.thcmenezes.audioria_api.model.entity.AlbumRating;
import com.github.thcmenezes.audioria_api.model.entity.Artist;
import com.github.thcmenezes.audioria_api.model.mapper.AlbumMapper;
import com.github.thcmenezes.audioria_api.repository.AlbumRatingRepository;
import com.github.thcmenezes.audioria_api.repository.AlbumRepository;
import com.github.thcmenezes.audioria_api.repository.ArtistRepository;
import com.github.thcmenezes.audioria_api.exception.AlbumException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AlbumService {
    private final AlbumRepository albumRepository;
    private final ArtistRepository artistRepository;
    private final AlbumRatingRepository albumRatingRepository;

    public Album create(AlbumDTO albumData) {
        Artist artist = artistRepository.findById(albumData.artistId())
                .orElseThrow(() -> new EntityNotFoundException("Artist not found"));

        boolean albumExists = albumRepository.existsByTitleIgnoreCaseAndArtistId(albumData.title(), artist.getId());
        if (albumExists) {
            throw AlbumException.alreadyExists(albumData.title(), artist.getName());
        }

        Album album = AlbumMapper.toEntity(albumData, artist);

        return albumRepository.save(album);
    }

    public List<Album> findAll() {
        return albumRepository.findAll();
    }

    public Optional<Album> findById(UUID id) {
        return albumRepository.findById(id);
    }

    public void update(UUID id, AlbumDTO albumData) {
        Album album = albumRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Album not found"));

        boolean albumExists = albumRepository.existsByTitleIgnoreCaseAndArtistIdAndIdNot(
                albumData.title(), album.getArtist().getId(), id
        );
        if (albumExists) {
            throw AlbumException.alreadyExists(albumData.title(), album.getArtist().getName());
        }

        BeanUtils.copyProperties(albumData, album);

        albumRepository.save(album);
    }

    public void delete(Album album) {
        albumRepository.delete(album);
    }

    public List<Album> findByArtistId(UUID artistId) {
        return albumRepository.findByArtistId(artistId);
    }

    public List<Album> findByArtistName(String artistName) {
        return albumRepository.findByArtistNameIgnoreCase(artistName);
    }

    public List<Album> getAlbumsRanking() {
        List<Album> albums = albumRepository.findAll();

        albums.forEach(album -> {
            int score = albumRatingRepository.findByAlbumId(album.getId())
                    .stream()
                    .mapToInt(AlbumRating::getScore)
                    .sum();
            album.setTempScore(score);
        });

        return albums.stream()
                .sorted((a, b) -> Integer.compare(b.getTempScore(), a.getTempScore()))
                .toList();
    }

}
