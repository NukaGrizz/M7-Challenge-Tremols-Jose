package com.trilogy.musicstorerecommendations.repository;



import com.trilogy.musicstorerecommendations.model.LabelRecommendation;
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
public class LabelRepositoryTests {

    @Autowired
    LabelRecommendationRepository labelRecommendationRepository;

    @Before
    public void setUp() throws Exception {
        labelRecommendationRepository.deleteAll();
    }

    @Test
    public void addGetDeleteLabel() {

        LabelRecommendation label = new LabelRecommendation();
        label.setLabelId(1);
        label.setUserId(1);
        label.setLiked(true);
        labelRecommendationRepository.save(label);

        Optional<LabelRecommendation> label1 = labelRecommendationRepository.findById(label.getId());

        assertEquals(label1.get(), label);

        labelRecommendationRepository.deleteById(label.getId());

        label1 = labelRecommendationRepository.findById(label.getId());

        assertFalse(label1.isPresent());

    }

    @Test
    public void getAllLabels() {

        LabelRecommendation label = new LabelRecommendation();
        label.setLabelId(2);
        label.setUserId(2);
        label.setLiked(true);
        labelRecommendationRepository.save(label);

        label = new LabelRecommendation();
        label.setLabelId(1);
        label.setUserId(1);
        label.setLiked(true);
        labelRecommendationRepository.save(label);

        List<LabelRecommendation> lList = labelRecommendationRepository.findAll();

        assertEquals(lList.size(), 2);

    }

    @Test
    public void updateLabel() {

        LabelRecommendation label = new LabelRecommendation();
        label.setLabelId(1);
        label.setUserId(1);
        label.setLiked(true);
        labelRecommendationRepository.save(label);

        label.setLabelId(1);
        label.setUserId(1);
        label.setLiked(false);
        labelRecommendationRepository.save(label);

        Optional<LabelRecommendation> label1 = labelRecommendationRepository.findById(label.getId());

        assertEquals(label1.get(), label);
    }
}