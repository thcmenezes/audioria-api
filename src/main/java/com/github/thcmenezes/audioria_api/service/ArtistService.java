package com.github.thcmenezes.audioria_api.service;

import com.github.thcmenezes.audioria_api.exception.ArtistException;
import com.github.thcmenezes.audioria_api.model.dto.ArtistDTO;
import com.github.thcmenezes.audioria_api.model.entity.Artist;
import com.github.thcmenezes.audioria_api.model.mapper.ArtistMapper;
import com.github.thcmenezes.audioria_api.repository.ArtistRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ArtistService {
    private final ArtistRepository artistRepository;

    public Artist create(ArtistDTO artistData) {
        if (artistRepository.existsByNameIgnoreCase(artistData.name())) {
            throw ArtistException.alreadyExists(artistData.name());
        }

        Artist artist = ArtistMapper.toEntity(artistData);

        return artistRepository.save(artist);
    }

    public List<Artist> findAll() {
        return artistRepository.findAll();
    }

    public Optional<Artist> findById(UUID id) {
        return artistRepository.findById(id);
    }

    public void update(UUID id, ArtistDTO artistData) {
        boolean artistExists = artistRepository.existsByNameIgnoreCaseAndIdNot(artistData.name(), id);

        if (artistExists) {
            throw ArtistException.alreadyExists(artistData.name());
        }

        Artist artist = artistRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Artist not found"));

        BeanUtils.copyProperties(artistData, artist);

        artistRepository.save(artist);
    }

    public void delete(Artist artist) {
        artistRepository.delete(artist);
    }
}
