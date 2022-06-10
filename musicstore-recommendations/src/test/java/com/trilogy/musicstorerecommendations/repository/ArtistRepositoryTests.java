package com.trilogy.musicstorerecommendations.repository;



import com.trilogy.musicstorerecommendations.model.ArtistRecommendation;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ArtistRepositoryTests {

    @Autowired
    ArtistRecommendationRepository artistRecommendationRepository;


    @Before
    public void setUp() throws Exception {
        artistRecommendationRepository.deleteAll();
    }

    @Test
    public void addGetDeleteArtist() {

        ArtistRecommendation artist = new ArtistRecommendation();
        artist.setArtistId(1);
        artist.setUserId(1);
        artist.setLiked(true);



        artist = artistRecommendationRepository.save(artist);

        Optional<ArtistRecommendation> artist1 = artistRecommendationRepository.findById(artist.getId());

        assertEquals(artist1.get(), artist);

        artistRecommendationRepository.deleteById(artist.getId());

        artist1 = artistRecommendationRepository.findById(artist.getId());

        assertFalse(artist1.isPresent());
    }

    @Test
    public void updateArtist() {

        ArtistRecommendation artist = new ArtistRecommendation();
        artist.setArtistId(1);
        artist.setUserId(1);
        artist.setLiked(true);

        artist = artistRecommendationRepository.save(artist);

        artist.setArtistId(1);
        artist.setUserId(1);
        artist.setLiked(true);

        artistRecommendationRepository.save(artist);

        Optional<ArtistRecommendation> artist1 = artistRecommendationRepository.findById(artist.getId());
        assertEquals(artist1.get(), artist);
    }

    @Test
    public void getAllArtists() {

        ArtistRecommendation artist = new ArtistRecommendation();
        artist.setArtistId(1);
        artist.setUserId(1);
        artist.setLiked(true);

        artist = artistRecommendationRepository.save(artist);

        artist = new ArtistRecommendation();
        artist.setArtistId(1);
        artist.setUserId(1);
        artist.setLiked(true);

        artist = artistRecommendationRepository.save(artist);

        List<ArtistRecommendation> aList = artistRecommendationRepository.findAll();
        assertEquals(aList.size(), 2);

    }

}