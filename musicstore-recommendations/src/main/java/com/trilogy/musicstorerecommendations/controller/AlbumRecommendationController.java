package com.trilogy.musicstorerecommendations.controller;


import com.trilogy.musicstorerecommendations.exception.BadIdException;
import com.trilogy.musicstorerecommendations.model.AlbumRecommendation;
import com.trilogy.musicstorerecommendations.repository.AlbumRecommendationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/albumrecommendation")
public class AlbumRecommendationController {
    @Autowired
    private AlbumRecommendationRepository repo;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AlbumRecommendation createAlbumRecommendation(@RequestBody AlbumRecommendation albumRecommendation) {
        return repo.save(albumRecommendation);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<AlbumRecommendation> getAllAlbumRecommendation() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AlbumRecommendation getAlbumRecommendationById(@PathVariable int id) {
        Optional<AlbumRecommendation> optionalAlbum = repo.findById(id);
        if (optionalAlbum.isPresent() == false) {
            throw new BadIdException("there is no album with id " + id);
        }
        return optionalAlbum.get();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateAlbumRecommendation(@PathVariable int id, @RequestBody AlbumRecommendation albumRecommendation) {
        if (albumRecommendation.getId() == null) {
            albumRecommendation.setId(id);
        } else if (albumRecommendation.getId() != id) {
            throw new BadIdException("The id in your path (" + id + ") is " +
                    "different from the id in your body (" + albumRecommendation.getId() + ").");
        }

        repo.save(albumRecommendation);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeAlbumRecommendation(@PathVariable int id) {
        repo.deleteById(id);
    }

}
