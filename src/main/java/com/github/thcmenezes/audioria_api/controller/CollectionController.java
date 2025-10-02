package com.github.thcmenezes.audioria_api.controller;

import com.github.thcmenezes.audioria_api.model.dto.request.CollectionRequestDTO;
import com.github.thcmenezes.audioria_api.model.dto.response.CollectionResponseDTO;
import com.github.thcmenezes.audioria_api.model.entity.Collection;
import com.github.thcmenezes.audioria_api.model.mapper.CollectionMapper;
import com.github.thcmenezes.audioria_api.service.CollectionService;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("api/collections")
public class CollectionController {
    private final CollectionService service;

    public CollectionController(CollectionService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<CollectionResponseDTO> create(@RequestBody CollectionRequestDTO collectionData) {
        var collection = CollectionMapper.toEntity(collectionData);
        collection = service.create(collection);

        return ResponseEntity
                .created(URI.create("/api/collections/" + collection.getId()))
                .body(CollectionMapper.toResponseDTO(collection));
    }

    @GetMapping
    public ResponseEntity<List<CollectionResponseDTO>> findAll() {
        List<Collection> collections = service.findAll();

        return ResponseEntity.ok(collections.stream().map(CollectionMapper::toResponseDTO).toList());
    }

    @GetMapping("{id}")
    public ResponseEntity<CollectionResponseDTO> findById(@PathVariable String id) {
        var uuidCollection = UUID.fromString(id);

        Optional<Collection> collection = service.findById(uuidCollection);

        return collection.map(foundCollection -> ResponseEntity.ok(CollectionMapper.toResponseDTO(foundCollection)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> update(@PathVariable String id, @RequestBody CollectionRequestDTO collectionData) {
        var uuidCollection = UUID.fromString(id);

        Optional<Collection> collection = service.findById(uuidCollection);

        if (collection.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        service.update(uuidCollection, collectionData);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        var uuidCollection = UUID.fromString(id);

        service.findById(uuidCollection).ifPresent(service::delete);

        return ResponseEntity.noContent().build();
    }
}
