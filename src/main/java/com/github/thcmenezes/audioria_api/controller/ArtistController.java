package com.github.thcmenezes.audioria_api.controller;

import com.github.thcmenezes.audioria_api.model.dto.ArtistDTO;
import com.github.thcmenezes.audioria_api.model.dto.request.ArtistRequestDTO;
import com.github.thcmenezes.audioria_api.model.dto.response.AlbumResponseDTO;
import com.github.thcmenezes.audioria_api.model.dto.response.ArtistResponseDTO;
import com.github.thcmenezes.audioria_api.model.entity.Album;
import com.github.thcmenezes.audioria_api.model.entity.Artist;
import com.github.thcmenezes.audioria_api.model.mapper.AlbumMapper;
import com.github.thcmenezes.audioria_api.model.mapper.ArtistMapper;
import com.github.thcmenezes.audioria_api.service.AlbumService;
import com.github.thcmenezes.audioria_api.service.ArtistService;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/artists")
public class ArtistController {

    private final ArtistService artistService;
    private final AlbumService albumService;

    public ArtistController(ArtistService artistService, AlbumService albumService) {
        this.artistService = artistService;
        this.albumService = albumService;
    }

    @PostMapping
    public ResponseEntity<ArtistResponseDTO> create(@RequestBody ArtistRequestDTO request) {
        ArtistDTO dto = ArtistMapper.fromRequestDTO(request);

        Artist artist = artistService.create(dto);

        return ResponseEntity
                .created(URI.create("/api/artists/" + artist.getId()))
                .body(ArtistMapper.toResponseDTO(artist));
    }

    @GetMapping
    public ResponseEntity<List<ArtistResponseDTO>> findAll() {
        List<Artist> artists = artistService.findAll();

        return ResponseEntity.ok(artists.stream().map(ArtistMapper::toResponseDTO).toList());
    }

    @GetMapping("{id}")
    public ResponseEntity<ArtistResponseDTO> findById(@PathVariable String id) {
        var uuidArtist = UUID.fromString(id);

        Optional<Artist> artist = artistService.findById(uuidArtist);

        return artist.map(foundArtist -> ResponseEntity.ok(ArtistMapper.toResponseDTO(foundArtist)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> update(@PathVariable String id, @RequestBody ArtistRequestDTO artistData) {
        var uuidArtist = UUID.fromString(id);

        Optional<Artist> artist = artistService.findById(uuidArtist);

        if (artist.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        artistService.update(uuidArtist, ArtistMapper.fromRequestDTO(artistData));

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        var uuidArtist = UUID.fromString(id);

        artistService.findById(uuidArtist).ifPresent(artistService::delete);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/artists/{id}/albums")
    public ResponseEntity<List<AlbumResponseDTO>> getAlbumsByArtistId(@PathVariable String id) {
        var uuidArtist = UUID.fromString(id);

        List<Album> albums = albumService.findByArtistId(uuidArtist);

        return ResponseEntity.ok(albums.stream().map(AlbumMapper::toResponseDTO).toList());
    }
}
