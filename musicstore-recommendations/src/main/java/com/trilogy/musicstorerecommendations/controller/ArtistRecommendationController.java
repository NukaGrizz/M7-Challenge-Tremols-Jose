package com.trilogy.musicstorerecommendations.controller;


import com.trilogy.musicstorerecommendations.exception.BadIdException;
import com.trilogy.musicstorerecommendations.model.ArtistRecommendation;
import com.trilogy.musicstorerecommendations.repository.ArtistRecommendationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/artistrecommendation")
public class ArtistRecommendationController {
    @Autowired
    private ArtistRecommendationRepository repo;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ArtistRecommendation createArtist(@RequestBody ArtistRecommendation artistRecommendation) {
        return repo.save(artistRecommendation);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ArtistRecommendation> getAllArtistRecommendation() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ArtistRecommendation getArtistRecommendationById(@PathVariable int id) {
        Optional<ArtistRecommendation> optionalArtist = repo.findById(id);
        if (optionalArtist.isPresent() == false) {
            throw new BadIdException("there is no artist with id " + id);
        }
        return optionalArtist.get();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateArtistRecommendation(@PathVariable int id, @RequestBody ArtistRecommendation artistRecommendation) {
        if (artistRecommendation.getId() == null) {
            artistRecommendation.setId(id);
        } else if (artistRecommendation.getId() != id) {
            throw new BadIdException("The id in your path (" + id + ") is " +
                    "different from the id in your body (" + artistRecommendation.getId() + ").");
        }

        repo.save(artistRecommendation);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeArtistRecommendation(@PathVariable int id) {
        repo.deleteById(id);
    }
}
