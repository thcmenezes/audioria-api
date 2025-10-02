package com.github.thcmenezes.audioria_api.service;

import com.github.thcmenezes.audioria_api.model.dto.request.CollectionRequestDTO;

import com.github.thcmenezes.audioria_api.model.entity.Collection;
import com.github.thcmenezes.audioria_api.repository.CollectionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CollectionService {
    private final CollectionRepository collectionRepository;

    public Collection create(Collection collection) {
        return collectionRepository.save(collection);
    }

    public List<Collection> findAll() {
        return collectionRepository.findAll();
    }

    public Optional<Collection> findById(UUID id) {
        return collectionRepository.findById(id);
    }

    public void update(UUID id, CollectionRequestDTO collectionData) {
        Collection collection = collectionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Collection not found"));

        BeanUtils.copyProperties(collectionData, collection);

        collectionRepository.save(collection);
    }

    public void delete(Collection collection) {
        collectionRepository.delete(collection);
    }
}
