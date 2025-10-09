package com.github.thcmenezes.audioria_api.controller;

import com.github.thcmenezes.audioria_api.model.dto.AlbumDTO;
import com.github.thcmenezes.audioria_api.model.dto.request.AlbumRequestDTO;
import com.github.thcmenezes.audioria_api.model.dto.response.AlbumResponseDTO;
import com.github.thcmenezes.audioria_api.model.entity.Album;
import com.github.thcmenezes.audioria_api.model.mapper.AlbumMapper;
import com.github.thcmenezes.audioria_api.service.AlbumRatingService;
import com.github.thcmenezes.audioria_api.service.AlbumService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("api/albums")
public class AlbumController {
    private final AlbumService service;
    private final AlbumRatingService albumRatingService;

    public AlbumController(AlbumService service, AlbumRatingService albumRatingService) {
        this.service = service;
        this.albumRatingService = albumRatingService;
    }

    @PostMapping
    public ResponseEntity<AlbumResponseDTO> create(@RequestBody AlbumRequestDTO request) {
        AlbumDTO albumDTO = AlbumMapper.fromRequestDTO(request);
        Album album = service.create(albumDTO);

        return ResponseEntity
                .created(URI.create("/api/albums/" + album.getId()))
                .body(AlbumMapper.toResponseDTO(album));
    }

    @GetMapping
    public ResponseEntity<List<AlbumResponseDTO>> findAll() {
        List<Album> albums = service.findAll();

        return ResponseEntity.ok(albums.stream().map(AlbumMapper::toResponseDTO).toList());
    }

    @GetMapping("{id}")
    public ResponseEntity<AlbumResponseDTO> findById(@PathVariable String id) {
        var uuidAlbum = UUID.fromString(id);

        Optional<Album> album = service.findById(uuidAlbum);

        return album.map(foundAlbum -> ResponseEntity.ok(AlbumMapper.toResponseDTO(foundAlbum)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> update(@PathVariable String id, @RequestBody AlbumRequestDTO albumData) {
        var uuidAlbum = UUID.fromString(id);

        Optional<Album> album = service.findById(uuidAlbum);

        if (album.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        service.update(uuidAlbum, AlbumMapper.fromRequestDTO(albumData));

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        var uuidAlbum = UUID.fromString(id);

        service.findById(uuidAlbum).ifPresent(service::delete);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/by-artist")
    public ResponseEntity<List<AlbumResponseDTO>> getAlbumsByArtist(@RequestParam String artistName) {
        List<Album> albums = service.findByArtistName(artistName);

        if (albums.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(albums.stream().map(AlbumMapper::toResponseDTO).toList());
    }

    @PostMapping("/{albumId}/ratings")
    public ResponseEntity<Void> addRating(
            @PathVariable UUID albumId,
            @RequestParam UUID ratingSourceId) {

        boolean added = albumRatingService.addRating(albumId, ratingSourceId);

        if (added) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @DeleteMapping("{albumId}/ratings")
    public ResponseEntity<Void> removeRating(
            @PathVariable String albumId,
            @RequestParam UUID sourceId) {

        boolean removed = albumRatingService.removeRating(UUID.fromString(albumId), sourceId);

        if (removed) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/ranking")
    public ResponseEntity<List<AlbumResponseDTO>> getAlbumsRanking() {
        List<Album> albums = service.getAlbumsRanking();

        if (albums.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(albums.stream().map(AlbumMapper::toResponseDTO).toList());
    }

}
