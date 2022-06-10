package com.trilogy.musicstorecatalog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trilogy.musicstorecatalog.model.Label;
import com.trilogy.musicstorecatalog.model.Track;
import com.trilogy.musicstorecatalog.repository.LabelRepository;
import com.trilogy.musicstorecatalog.repository.TrackRepository;
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
@WebMvcTest(TrackController.class)
public class TrackControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    TrackRepository trackRepo;

    private ObjectMapper mapper = new ObjectMapper();

    private Track mockOutput;
    private Track createInput;
    private Track createOutput;
    private List<Track> aList = aList = new ArrayList<>();;

    private String allTrackJson;
    private String mockOutputJson;
    private String createInputJson;
    private String createOutputJson;

    @Before
    public void setup() throws Exception {
        mockOutput = new Track(1,1,"the greatest track",120);
        mockOutputJson = mapper.writeValueAsString(mockOutput);

        createInput = new Track();
        createInput.setAlbumId(2);
        createInput.setTitle("the second greatest track");
        createInput.setRuntime(140);
        createInputJson = mapper.writeValueAsString(createInput);

        createOutput = new Track(2,2,"the second greatest track",140);
        createOutputJson = mapper.writeValueAsString(createOutput);

        aList.add(mockOutput);
        aList.add(createOutput);
        allTrackJson = mapper.writeValueAsString(aList);

        doReturn(Optional.of(mockOutput)).when(trackRepo).findById(1);
        doReturn(createOutput).when(trackRepo).save(createInput);
        doReturn(aList).when(trackRepo).findAll();

    }


    @Test
    public void createTrack() throws Exception {
        mockMvc.perform(post("/track")
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
                        get("/track"))
                .andExpect(status().isOk())
                .andExpect((content().json(allTrackJson)));
    }

    @Test
    public void getTrackById() throws Exception {
        mockMvc.perform(
                        get("/track/1"))
                .andExpect(status().isOk())
                .andExpect((content().json(mockOutputJson)));
    }

    @Test
    public void updateTrack() throws Exception {
        mockMvc.perform(
                        put("/track/2")
                                .content(createInputJson)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNoContent());
    }

    @Test
    public void removeTrack() throws Exception {
        mockMvc.perform(delete("/track/2"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void ShouldReturn404WhenRequestTrackIdNotExist() throws Exception {
        mockMvc.perform(get("/album/5"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

}