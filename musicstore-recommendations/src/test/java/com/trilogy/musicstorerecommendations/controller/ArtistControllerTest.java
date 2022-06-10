package com.trilogy.musicstorerecommendations.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.trilogy.musicstorerecommendations.model.ArtistRecommendation;
import com.trilogy.musicstorerecommendations.repository.ArtistRecommendationRepository;
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
@WebMvcTest(ArtistRecommendationController.class)
public class ArtistControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ArtistRecommendationRepository artistRepo;

    private ObjectMapper mapper = new ObjectMapper();

    private ArtistRecommendation mockOutput;
    private ArtistRecommendation createInput;
    private ArtistRecommendation createOutput;
    private List<ArtistRecommendation> aList = aList = new ArrayList<>();;

    private String allArtistRecommendationJson;
    private String mockOutputJson;
    private String createInputJson;
    private String createOutputJson;

    @Before
    public void setup() throws Exception {
        mockOutput = new ArtistRecommendation(1,1,1,true);
        mockOutputJson = mapper.writeValueAsString(mockOutput);

        createInput = new ArtistRecommendation();
        createInput.setArtistId(2);
        createInput.setUserId(2);
        createInput.setLiked(true);
        createInputJson = mapper.writeValueAsString(createInput);

        createOutput = new ArtistRecommendation(2,2,2,true);
        createOutputJson = mapper.writeValueAsString(createOutput);

        aList.add(mockOutput);
        aList.add(createOutput);
        allArtistRecommendationJson = mapper.writeValueAsString(aList);

        doReturn(Optional.of(mockOutput)).when(artistRepo).findById(1);
        doReturn(createOutput).when(artistRepo).save(createInput);
        doReturn(aList).when(artistRepo).findAll();

    }


    @Test
    public void createArtist() throws Exception {
        mockMvc.perform(post("/artistrecommendation")
                        .content(createInputJson)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(createOutputJson));

    }

    @Test
    public void getAllArtist() throws Exception {
        mockMvc.perform(
                        get("/artistrecommendation"))
                .andExpect(status().isOk())
                .andExpect((content().json(allArtistRecommendationJson)));
    }

    @Test
    public void getArtistById() throws Exception {
        mockMvc.perform(
                        get("/artistrecommendation/1"))
                .andExpect(status().isOk())
                .andExpect((content().json(mockOutputJson)));
    }

    @Test
    public void updateArtist() throws Exception {
        mockMvc.perform(
                        put("/artistrecommendation/2")
                                .content(createInputJson)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNoContent());
    }

    @Test
    public void removeArtist() throws Exception {
        mockMvc.perform(delete("/artistrecommendation/2"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void ShouldReturn404WhenRequestArtistIdNotExist() throws Exception {
        mockMvc.perform(get("/artistrecommendation/7"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}