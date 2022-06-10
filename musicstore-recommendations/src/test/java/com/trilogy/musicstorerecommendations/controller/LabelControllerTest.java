package com.trilogy.musicstorerecommendations.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trilogy.musicstorerecommendations.model.LabelRecommendation;
import com.trilogy.musicstorerecommendations.repository.LabelRecommendationRepository;
import com.trilogy.musicstorerecommendations.repository.LabelRepositoryTests;
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
@WebMvcTest(LabelRecommendationController.class)
public class LabelControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    LabelRecommendationRepository labelRepo;

    private ObjectMapper mapper = new ObjectMapper();

    private LabelRecommendation mockOutput;
    private LabelRecommendation createInput;
    private LabelRecommendation createOutput;
    private List<LabelRecommendation> aList = aList = new ArrayList<>();;

    private String allLabelRecommendationJson;
    private String mockOutputJson;
    private String createInputJson;
    private String createOutputJson;

    @Before
    public void setup() throws Exception {
        mockOutput = new LabelRecommendation(1,1,1,true);
        mockOutputJson = mapper.writeValueAsString(mockOutput);

        createInput = new LabelRecommendation();
        createInput.setLabelId(2);
        createInput.setUserId(2);
        createInput.setLiked(true);
        createInputJson = mapper.writeValueAsString(createInput);

        createOutput = new LabelRecommendation(2,2,2,true);
        createOutputJson = mapper.writeValueAsString(createOutput);

        aList.add(mockOutput);
        aList.add(createOutput);
        allLabelRecommendationJson = mapper.writeValueAsString(aList);

        doReturn(Optional.of(mockOutput)).when(labelRepo).findById(1);
        doReturn(createOutput).when(labelRepo).save(createInput);
        doReturn(aList).when(labelRepo).findAll();

    }


    @Test
    public void createLabel() throws Exception {
        mockMvc.perform(post("/labelrecommendation")
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
                        get("/labelrecommendation"))
                .andExpect(status().isOk())
                .andExpect((content().json(allLabelRecommendationJson)));
    }

    @Test
    public void getLabelById() throws Exception {
        mockMvc.perform(
                        get("/labelrecommendation/1"))
                .andExpect(status().isOk())
                .andExpect((content().json(mockOutputJson)));
    }

    @Test
    public void updateLabel() throws Exception {
        mockMvc.perform(
                        put("/labelrecommendation/2")
                                .content(createInputJson)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNoContent());
    }

    @Test
    public void removeLabel() throws Exception {
        mockMvc.perform(delete("/labelrecommendation/2"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void ShouldReturn404WhenRequestLabelIdNotExist() throws Exception {
        mockMvc.perform(get("/labelrecommendation/5"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}