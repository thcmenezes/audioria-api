package com.github.thcmenezes.audioria_api.repository;

import com.github.thcmenezes.audioria_api.model.entity.Artist;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@RequiredArgsConstructor
class ArtistRepositoryTest {

    ArtistRepository artistRepository;

    @Test
    public void saveTest() {
        Artist artist = new Artist();
        artist.setName("Pearl Jam");

        var artistSaved = artistRepository.save(artist);

    }
}
