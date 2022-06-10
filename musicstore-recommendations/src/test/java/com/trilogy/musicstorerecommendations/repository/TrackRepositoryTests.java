package com.trilogy.musicstorerecommendations.repository;

import com.trilogy.musicstorerecommendations.model.TrackRecommendation;
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
public class TrackRepositoryTests {

    @Autowired
    TrackRecommendationRepository trackRecommendationRepository;

    @Before
    public void setUp() throws Exception {
        trackRecommendationRepository.deleteAll();
    }

    @Test
    public void addGetDeleteTrack() {

        TrackRecommendation track = new TrackRecommendation();
        track.setTrackId(1);
        track.setUserId(1);
        track.setLiked(true);
        track = trackRecommendationRepository.save(track);

        Optional<TrackRecommendation> track1 = trackRecommendationRepository.findById(track.getId());

        assertEquals(track1.get(), track);

        trackRecommendationRepository.deleteById(track.getId());

        track1 = trackRecommendationRepository.findById(track.getId());

        assertFalse(track1.isPresent());
    }

    @Test
    public void updateTrack() {

        TrackRecommendation track = new TrackRecommendation();
        track.setTrackId(1);
        track.setUserId(2);
        track.setLiked(true);
        track = trackRecommendationRepository.save(track);

        track.setTrackId(1);
        track.setUserId(2);
        track.setLiked(false);

        trackRecommendationRepository.save(track);

        Optional<TrackRecommendation> track1 = trackRecommendationRepository.findById(track.getId());

        assertEquals(track1.get(), track);

    }

    @Test
    public void getAllTracks() {

        TrackRecommendation track = new TrackRecommendation();
        track.setTrackId(1);
        track.setUserId(1);
        track.setLiked(true);
        track = trackRecommendationRepository.save(track);

        track = new TrackRecommendation();
        track.setTrackId(2);
        track.setUserId(1);
        track.setLiked(false);
        track = trackRecommendationRepository.save(track);

        List<TrackRecommendation> tList = trackRecommendationRepository.findAll();

        assertEquals(tList.size(), 2);

    }

}