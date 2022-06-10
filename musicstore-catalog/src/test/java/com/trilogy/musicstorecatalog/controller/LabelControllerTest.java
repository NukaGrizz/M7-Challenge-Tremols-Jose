package com.trilogy.musicstorecatalog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trilogy.musicstorecatalog.model.Artist;
import com.trilogy.musicstorecatalog.model.Label;
import com.trilogy.musicstorecatalog.repository.ArtistRepository;
import com.trilogy.musicstorecatalog.repository.LabelRepository;
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
@WebMvcTest(LabelController.class)
public class LabelControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    LabelRepository labelRepo;

    private ObjectMapper mapper = new ObjectMapper();

    private Label mockOutput;
    private Label createInput;
    private Label createOutput;
    private List<Label> aList = aList = new ArrayList<>();;

    private String allLabelJson;
    private String mockOutputJson;
    private String createInputJson;
    private String createOutputJson;

    @Before
    public void setup() throws Exception {
        mockOutput = new Label(1,"Dans","www.Dans.com");
        mockOutputJson = mapper.writeValueAsString(mockOutput);

        createInput = new Label();
        createInput.setName("Same");
        createInput.setWebsite("www.same.com");
        createInputJson = mapper.writeValueAsString(createInput);

        createOutput = new Label(2,"Same","www.Same.com");
        createOutputJson = mapper.writeValueAsString(createOutput);

        aList.add(mockOutput);
        aList.add(createOutput);
        allLabelJson = mapper.writeValueAsString(aList);

        doReturn(Optional.of(mockOutput)).when(labelRepo).findById(1);
        doReturn(createOutput).when(labelRepo).save(createInput);
        doReturn(aList).when(labelRepo).findAll();

    }


    @Test
    public void createLabel() throws Exception {
        mockMvc.perform(post("/label")
                        .content(createInputJson)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(createOutputJson));

    }

    @Test
    public void getAllLabel() throws Exception {
        mockMvc.perform(
                        get("/label"))
                .andExpect(status().isOk())
                .andExpect((content().json(allLabelJson)));
    }

    @Test
    public void getLabelById() throws Exception {
        mockMvc.perform(
                        get("/label/1"))
                .andExpect(status().isOk())
                .andExpect((content().json(mockOutputJson)));
    }

    @Test
    public void updateLabel() throws Exception {
        mockMvc.perform(
                        put("/label/2")
                                .content(createInputJson)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNoContent());
    }

    @Test
    public void removeLabel() throws Exception {
        mockMvc.perform(delete("/label/2"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void ShouldReturn404WhenRequestLabelIdNotExist() throws Exception {
        mockMvc.perform(get("/label/5"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}