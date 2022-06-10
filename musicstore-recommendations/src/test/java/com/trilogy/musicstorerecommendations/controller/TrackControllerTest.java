package com.trilogy.musicstorerecommendations.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.trilogy.musicstorerecommendations.model.TrackRecommendation;
import com.trilogy.musicstorerecommendations.repository.TrackRecommendationRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TrackRecommendationController.class)
public class TrackControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    TrackRecommendationRepository trackRepo;

    private ObjectMapper mapper = new ObjectMapper();

    private TrackRecommendation mockOutput;
    private TrackRecommendation createInput;
    private TrackRecommendation createOutput;
    private List<TrackRecommendation> aList = aList = new ArrayList<>();;

    private String allTrackRecommendationJson;
    private String mockOutputJson;
    private String createInputJson;
    private String createOutputJson;

    @Before
    public void setup() throws Exception {
        mockOutput = new TrackRecommendation(1,1,1,true);
        mockOutputJson = mapper.writeValueAsString(mockOutput);

        createInput = new TrackRecommendation();
        createInput.setTrackId(2);
        createInput.setUserId(2);
        createInput.setLiked(true);
        createInputJson = mapper.writeValueAsString(createInput);

        createOutput = new TrackRecommendation(2,2,2,true);
        createOutputJson = mapper.writeValueAsString(createOutput);

        aList.add(mockOutput);
        aList.add(createOutput);
        allTrackRecommendationJson = mapper.writeValueAsString(aList);

        doReturn(Optional.of(mockOutput)).when(trackRepo).findById(1);
        doReturn(createOutput).when(trackRepo).save(createInput);
        doReturn(aList).when(trackRepo).findAll();

    }


    @Test
    public void createTrack() throws Exception {
        mockMvc.perform(post("/trackrecommendation")
                        .content(createInputJson)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(createOutputJson));

    }

    @Test
    public void getAllTrack() throws Exception {
        mockMvc.perform(
                        get("/trackrecommendation"))
                .andExpect(status().isOk())
                .andExpect((content().json(allTrackRecommendationJson)));
    }

    @Test
    public void getTrackById() throws Exception {
        mockMvc.perform(
                        get("/trackrecommendation/1"))
                .andExpect(status().isOk())
                .andExpect((content().json(mockOutputJson)));
    }

    @Test
    public void updateTrack() throws Exception {
        mockMvc.perform(
                        put("/trackrecommendation/2")
                                .content(createInputJson)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNoContent());
    }

    @Test
    public void removeTrack() throws Exception {
        mockMvc.perform(delete("/trackrecommendation/2"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void ShouldReturn404WhenRequestTrackIdNotExist() throws Exception {
        mockMvc.perform(get("/trackrecommendation/5"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

}