package com.trilogy.musicstorerecommendations.controller;


import com.trilogy.musicstorerecommendations.exception.BadIdException;
import com.trilogy.musicstorerecommendations.model.LabelRecommendation;
import com.trilogy.musicstorerecommendations.repository.LabelRecommendationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/labelrecommendation")
public class LabelRecommendationController {
    @Autowired
    private LabelRecommendationRepository repo;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LabelRecommendation createLabel(@RequestBody LabelRecommendation label) {
        return repo.save(label);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<LabelRecommendation> getAllLabel() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public LabelRecommendation getLabelById(@PathVariable int id) {
        Optional<LabelRecommendation> optionalLabel = repo.findById(id);
        if (optionalLabel.isPresent() == false) {
            throw new BadIdException("there is no label with id " + id);
        }
        return optionalLabel.get();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateLabel(@PathVariable int id, @RequestBody LabelRecommendation labelRecommendation) {
        if (labelRecommendation.getId() == null) {
            labelRecommendation.setId(id);
        } else if (labelRecommendation.getId() != id) {
            throw new BadIdException("The id in your path (" + id + ") is " +
                    "different from the id in your body (" + labelRecommendation.getId() + ").");
        }

        repo.save(labelRecommendation);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeLabel(@PathVariable int id) {
        repo.deleteById(id);
    }
}
