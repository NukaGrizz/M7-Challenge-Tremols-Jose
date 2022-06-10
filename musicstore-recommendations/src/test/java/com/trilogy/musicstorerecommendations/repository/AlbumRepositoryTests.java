package com.trilogy.musicstorerecommendations.repository;



import com.trilogy.musicstorerecommendations.model.AlbumRecommendation;
import com.trilogy.musicstorerecommendations.model.ArtistRecommendation;
import com.trilogy.musicstorerecommendations.model.LabelRecommendation;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class AlbumRepositoryTests {

    @Autowired
    AlbumRecommendationRepository albumRecommendationRepository;

    @Before
    public void setUp() throws Exception {
        albumRecommendationRepository.deleteAll();
    }

    @Test
    public void addGetDeleteAlbum() {

        AlbumRecommendation album = new AlbumRecommendation();
        album.setAlbumId(1);
        album.setUserId(1);
        album.setLiked(true);

        album = albumRecommendationRepository.save(album);

        Optional<AlbumRecommendation> album1 = albumRecommendationRepository.findById(album.getId());

        assertEquals(album1.get(), album);

        albumRecommendationRepository.deleteById(album.getId());

        album1 = albumRecommendationRepository.findById(album.getId());

        assertFalse(album1.isPresent());

    }

    @Test
    public void getAllAlbums() {


        AlbumRecommendation album = new AlbumRecommendation();
        album.setAlbumId(1);
        album.setUserId(1);
        album.setLiked(true);

        album = albumRecommendationRepository.save(album);

        album = new AlbumRecommendation();
        album.setAlbumId(1);
        album.setUserId(1);
        album.setLiked(true);

        album = albumRecommendationRepository.save(album);

        List<AlbumRecommendation> aList = albumRecommendationRepository.findAll();

        assertEquals(aList.size(), 2);

    }

    @Test
    public void updateAlbum() {

        AlbumRecommendation album = new AlbumRecommendation();
        album.setAlbumId(1);
        album.setUserId(1);
        album.setLiked(true);

        album = albumRecommendationRepository.save(album);

        album.setAlbumId(1);
        album.setUserId(1);
        album.setLiked(false);

        albumRecommendationRepository.save(album);

        Optional<AlbumRecommendation> album1 = albumRecommendationRepository.findById(album.getId());
        assertEquals(album1.get(), album);

    }

}