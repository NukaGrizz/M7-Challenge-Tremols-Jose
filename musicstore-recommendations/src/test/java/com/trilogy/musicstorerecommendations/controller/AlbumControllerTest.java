package com.trilogy.musicstorerecommendations.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.trilogy.musicstorerecommendations.model.AlbumRecommendation;
import com.trilogy.musicstorerecommendations.repository.AlbumRecommendationRepository;
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
@WebMvcTest(AlbumRecommendationController.class)
public class AlbumControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    AlbumRecommendationRepository albumRepo;

    private ObjectMapper mapper = new ObjectMapper();

    private AlbumRecommendation mockOutput;
    private AlbumRecommendation createInput;
    private AlbumRecommendation createOutput;
    private List<AlbumRecommendation> aList = aList = new ArrayList<>();;

    private String allAlbumsRecommendationJson;
    private String mockOutputJson;
    private String createInputJson;
    private String createOutputJson;

    @Before
    public void setup() throws Exception {
        mockOutput = new AlbumRecommendation(1,1,1,true);
        mockOutputJson = mapper.writeValueAsString(mockOutput);

        createInput = new AlbumRecommendation();
        createInput.setAlbumId(2);
        createInput.setUserId(2);
        createInput.setLiked(true);
        createInputJson = mapper.writeValueAsString(createInput);

        createOutput = new AlbumRecommendation(2,2,2,true);
        createOutputJson = mapper.writeValueAsString(createOutput);

        aList.add(mockOutput);
        aList.add(createOutput);
        allAlbumsRecommendationJson = mapper.writeValueAsString(aList);

        doReturn(Optional.of(mockOutput)).when(albumRepo).findById(1);
        doReturn(createOutput).when(albumRepo).save(createInput);
        doReturn(aList).when(albumRepo).findAll();

    }


    @Test
    public void createAlbum() throws Exception {
        mockMvc.perform(post("/albumrecommendation")
                        .content(createInputJson)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(createOutputJson));

    }

    @Test
    public void getAllAlbums() throws Exception {
        mockMvc.perform(
                        get("/albumrecommendation"))
                .andExpect(status().isOk())
                .andExpect((content().json(allAlbumsRecommendationJson)));
    }

    @Test
    public void getAlbumById() throws Exception {
        mockMvc.perform(
                        get("/albumrecommendation/1"))
                        .andExpect(status().isOk())
                        .andExpect((content().json(mockOutputJson)));
    }

    @Test
    public void updateAlbum() throws Exception {
        mockMvc.perform(
                        put("/albumrecommendation/2")
                                .content(createInputJson)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNoContent());
    }

    @Test
    public void removeAlbum() throws Exception {
        mockMvc.perform(delete("/albumrecommendation/2"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void ShouldReturn404WhenRequestAlbumIdNotExist() throws Exception {
        mockMvc.perform(get("/albumrecommendation/5"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}