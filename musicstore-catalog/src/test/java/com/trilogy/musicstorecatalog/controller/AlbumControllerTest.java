package com.trilogy.musicstorecatalog.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trilogy.musicstorecatalog.model.Album;
import com.trilogy.musicstorecatalog.repository.AlbumRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import java.sql.Date;
import java.time.LocalDate;
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
@WebMvcTest(AlbumController.class)
public class AlbumControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    AlbumRepository albumRepo;

    private ObjectMapper mapper = new ObjectMapper();

    private Album mockOutput;
    private Album createInput;
    private Album createOutput;
    private List<Album> aList = aList = new ArrayList<>();;

    private String allAlbumsJson;
    private String mockOutputJson;
    private String createInputJson;
    private String createOutputJson;

    @Before
    public void setup() throws Exception {
        mockOutput = new Album(1,"testTitle",1, LocalDate.of(2022,1,1),1, BigDecimal.valueOf(9.99));
        mockOutputJson = mapper.writeValueAsString(mockOutput);

        createInput = new Album();
        createInput.setTitle("testTitle");
        createInput.setArtistId(2);
        createInput.setReleaseDate(LocalDate.of(2021,9,9));
        createInput.setLabelId(2);
        createInput.setListPrice(BigDecimal.valueOf(19.99));
        createInputJson = mapper.writeValueAsString(createInput);

        createOutput = new Album(2,"testTitle",2, LocalDate.of(2021, 9, 9),2, BigDecimal.valueOf(19.99));
        createOutputJson = mapper.writeValueAsString(createOutput);

        aList.add(mockOutput);
        aList.add(createOutput);
        allAlbumsJson = mapper.writeValueAsString(aList);

        doReturn(Optional.of(mockOutput)).when(albumRepo).findById(1);
        doReturn(createOutput).when(albumRepo).save(createInput);
        doReturn(aList).when(albumRepo).findAll();

    }


    @Test
    public void createAlbum() throws Exception {
        mockMvc.perform(post("/album")
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
                        get("/album"))
                .andExpect(status().isOk())
                .andExpect((content().json(allAlbumsJson)));
    }

    @Test
    public void getAlbumById() throws Exception {
        mockMvc.perform(
                        get("/album/1"))
                        .andExpect(status().isOk())
                        .andExpect((content().json(mockOutputJson)));
    }

    @Test
    public void updateAlbum() throws Exception {
        mockMvc.perform(
                        put("/album/2")
                                .content(createInputJson)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNoContent());
    }

    @Test
    public void removeAlbum() throws Exception {
        mockMvc.perform(delete("/album/2"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void ShouldReturn404WhenRequestAlbumIdNotExist() throws Exception {
        mockMvc.perform(get("/album/5"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}