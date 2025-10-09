package com.github.thcmenezes.audioria_api.controller;

import com.github.thcmenezes.audioria_api.model.dto.request.AlbumRatingSourceRequestDTO;
import com.github.thcmenezes.audioria_api.model.dto.response.AlbumRatingSourceResponseDTO;
import com.github.thcmenezes.audioria_api.model.dto.response.AlbumResponseDTO;
import com.github.thcmenezes.audioria_api.model.entity.Album;
import com.github.thcmenezes.audioria_api.model.entity.AlbumRatingSource;
import com.github.thcmenezes.audioria_api.model.mapper.AlbumMapper;
import com.github.thcmenezes.audioria_api.model.mapper.AlbumRatingSourceMapper;
import com.github.thcmenezes.audioria_api.service.AlbumRatingSourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/rating-sources")
public class AlbumRatingSourceController {
    private final AlbumRatingSourceService service;

    @PostMapping
    public ResponseEntity<AlbumRatingSourceResponseDTO> create(@RequestBody AlbumRatingSourceRequestDTO request) {
        AlbumRatingSource source = service.create(request);

        return ResponseEntity
                .created(URI.create("/api/rating-sources/" + source.getId()))
                .body(AlbumRatingSourceMapper.toResponseDTO(source));
    }

    @GetMapping
    public ResponseEntity<List<AlbumRatingSourceResponseDTO>> findAll() {
        List<AlbumRatingSource> sources = service.findAll();

        return ResponseEntity.ok(sources.stream().map(AlbumRatingSourceMapper::toResponseDTO).toList());
    }

    @GetMapping("{id}")
    public ResponseEntity<AlbumRatingSourceResponseDTO> findById(@PathVariable String id) {
        var uuidSource = UUID.fromString(id);

        Optional<AlbumRatingSource> source = service.findById(uuidSource);

        return source.map(foundSource -> ResponseEntity.ok(AlbumRatingSourceMapper.toResponseDTO(foundSource)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> update(@PathVariable String id, @RequestBody AlbumRatingSourceRequestDTO sourceData) {
        var uuidSource = UUID.fromString(id);

        Optional<AlbumRatingSource> source = service.findById(uuidSource);

        if (source.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        service.update(uuidSource, sourceData);

        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<Void> delete(@PathVariable String id) {
        var uuidSource = UUID.fromString(id);

        service.findById(uuidSource).ifPresent(service::delete);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{sourceId}/albums")
    public ResponseEntity<List<AlbumResponseDTO>> getAlbumsByArtistId(@PathVariable String sourceId) {
        var uuidSource = UUID.fromString(sourceId);

        List<Album> albums = service.getAlbumsBySource(uuidSource);

        if (albums.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(albums.stream().map(AlbumMapper::toResponseDTO).toList());
    }

    @DeleteMapping("/{sourceId}/ratings")
    public ResponseEntity<Void> removeRatingsBySource(@PathVariable UUID sourceId) {
        service.removeRatingsBySource(sourceId);
        return ResponseEntity.noContent().build();
    }

}
