package com.trilogy.musicstorerecommendations.controller;


import com.trilogy.musicstorerecommendations.exception.BadIdException;
import com.trilogy.musicstorerecommendations.model.TrackRecommendation;
import com.trilogy.musicstorerecommendations.repository.TrackRecommendationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/trackrecommendation")
public class TrackRecommendationController {
    @Autowired
    private TrackRecommendationRepository repo;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TrackRecommendation createTrackRecommendation(@RequestBody TrackRecommendation trackRecommendation) {
        return repo.save(trackRecommendation);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TrackRecommendation> getAllTrackRecommendation() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TrackRecommendation getTrackRecommendationById(@PathVariable int id) {
        Optional<TrackRecommendation> optionalTrack = repo.findById(id);
        if (optionalTrack.isPresent() == false) {
            throw new BadIdException("there is no track with id " + id);
        }
        return optionalTrack.get();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateTrackRecommendation(@PathVariable int id, @RequestBody TrackRecommendation trackrecommendation) {
        if (trackrecommendation.getId() == null) {
            trackrecommendation.setId(id);
        } else if (trackrecommendation.getId() != id) {
            throw new BadIdException("The id in your path (" + id + ") is " +
                    "different from the id in your body (" + trackrecommendation.getId() + ").");
        }

        repo.save(trackrecommendation);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeTrack(@PathVariable int id) {
        repo.deleteById(id);
    }
}
