package com.github.thcmenezes.audioria_api.service;

import com.github.thcmenezes.audioria_api.exception.AlbumRatingSourceException;
import com.github.thcmenezes.audioria_api.model.dto.request.AlbumRatingSourceRequestDTO;
import com.github.thcmenezes.audioria_api.model.entity.Album;
import com.github.thcmenezes.audioria_api.model.entity.AlbumRating;
import com.github.thcmenezes.audioria_api.model.entity.AlbumRatingSource;
import com.github.thcmenezes.audioria_api.model.mapper.AlbumRatingSourceMapper;
import com.github.thcmenezes.audioria_api.repository.AlbumRatingRepository;
import com.github.thcmenezes.audioria_api.repository.AlbumRatingSourceRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AlbumRatingSourceService {
    private final AlbumRatingSourceRepository ratingSourceRepository;
    private final AlbumRatingRepository albumRatingRepository;

    public AlbumRatingSource create(AlbumRatingSourceRequestDTO ratingSource) {
        if (ratingSourceRepository.existsByNameIgnoreCase(ratingSource.name())) {
            throw AlbumRatingSourceException.alreadyExists(ratingSource.name());
        }

        AlbumRatingSource entity = AlbumRatingSourceMapper.fromRequestDTO(ratingSource);
        return ratingSourceRepository.save(entity);
    }

    public List<AlbumRatingSource> findAll() {
        return ratingSourceRepository.findAll();
    }

    public Optional<AlbumRatingSource> findById(UUID id) {
        return ratingSourceRepository.findById(id);
    }

    public void update(UUID id, AlbumRatingSourceRequestDTO ratingSource) {
        boolean sourceExists = ratingSourceRepository.existsByNameIgnoreCaseAndIdNot(ratingSource.name(), id);

        if (sourceExists) {
            throw AlbumRatingSourceException.alreadyExists(ratingSource.name());
        }

        AlbumRatingSource entity = ratingSourceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Source not found"));

        BeanUtils.copyProperties(ratingSource, entity);

        ratingSourceRepository.save(entity);
    }

    public void delete(AlbumRatingSource ratingSource) {
        ratingSourceRepository.delete(ratingSource);
    }

    public List<Album> getAlbumsBySource(UUID sourceId) {
        return albumRatingRepository.findByRatingSourceId(sourceId)
                .stream()
                .map(AlbumRating::getAlbum)
                .toList();
    }

    public void removeRatingsBySource(UUID sourceId) {
        albumRatingRepository.deleteByRatingSourceId(sourceId);
    }

}
