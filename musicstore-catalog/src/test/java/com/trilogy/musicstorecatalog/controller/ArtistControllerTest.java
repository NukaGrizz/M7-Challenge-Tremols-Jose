package com.trilogy.musicstorecatalog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.deploy.uitoolkit.ui.ConsoleController;
import com.trilogy.musicstorecatalog.model.Artist;
import com.trilogy.musicstorecatalog.repository.ArtistRepository;
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
import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ArtistController.class)
public class ArtistControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ArtistRepository artistRepo;

    private ObjectMapper mapper = new ObjectMapper();

    private Artist mockOutput;
    private Artist createInput;
    private Artist createOutput;
    private List<Artist> aList = aList = new ArrayList<>();;

    private String allArtistJson;
    private String mockOutputJson;
    private String createInputJson;
    private String createOutputJson;

    @Before
    public void setup() throws Exception {
        mockOutput = new Artist(1,"porky","@porky","@porky");
        mockOutputJson = mapper.writeValueAsString(mockOutput);

        createInput = new Artist();
        createInput.setName("sam");
        createInput.setInstagram("@sam");
        createInput.setTwitter("@sam");
        createInputJson = mapper.writeValueAsString(createInput);

        createOutput = new Artist(2,"sam","@sam","@sam");
        createOutputJson = mapper.writeValueAsString(createOutput);

        aList.add(mockOutput);
        aList.add(createOutput);
        allArtistJson = mapper.writeValueAsString(aList);

        doReturn(Optional.of(mockOutput)).when(artistRepo).findById(1);
        doReturn(createOutput).when(artistRepo).save(createInput);
        doReturn(aList).when(artistRepo).findAll();

    }


    @Test
    public void createArtist() throws Exception {
        mockMvc.perform(post("/artist")
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
                        get("/artist"))
                .andExpect(status().isOk())
                .andExpect((content().json(allArtistJson)));
    }

    @Test
    public void getArtistById() throws Exception {
        mockMvc.perform(
                        get("/artist/1"))
                .andExpect(status().isOk())
                .andExpect((content().json(mockOutputJson)));
    }

    @Test
    public void updateArtist() throws Exception {
        mockMvc.perform(
                        put("/artist/2")
                                .content(createInputJson)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNoContent());
    }

    @Test
    public void removeArtist() throws Exception {
        mockMvc.perform(delete("/artist/2"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void ShouldReturn404WhenRequestArtistIdNotExist() throws Exception {
        mockMvc.perform(get("/artist/7"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}