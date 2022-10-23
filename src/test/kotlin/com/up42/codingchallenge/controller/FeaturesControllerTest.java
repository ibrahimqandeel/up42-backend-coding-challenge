package com.up42.codingchallenge.controller;

import com.up42.codingchallenge.dto.FeatureDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class FeaturesControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void test_getFeatures_endpoint() throws Exception {
        //when
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/features"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.features.length()").value(3))
                .andExpect(jsonPath("$.features.[0].id", equalTo("cf5dbe37-ab95-4af1-97ad-2637aec4ddf0")))
                .andExpect(jsonPath("$.features.[1].id", equalTo("ca81d759-0b8c-4b3f-a00a-0908a3ddd655")))
                .andExpect(jsonPath("$.features.[2].id", equalTo("b0d3bf6a-ff54-49e0-a4cb-e57dcb68d3b5")))
                .andReturn();
    }

    @Test
    public void test_getQuicklookField_endpoint() throws Exception {
        //given
        FeatureDTO feature = new FeatureDTO(
                UUID.fromString("cf5dbe37-ab95-4af1-97ad-2637aec4ddf0"),
                1556904743783L,
                1556904743783L,
                1556904768781L,
                "Sentinel-1B",
                "iVBORw0KGgoAAAANSUhEUgAAAgAAAAFSCAIAAAAL");

        byte[] expectedResult = Base64.getDecoder().decode(feature.getQuicklook());

        //when
        MvcResult mvcResponse = mockMvc.perform(MockMvcRequestBuilders
                        .get("/features/"+feature.getId()+"/quicklook")
                        .contentType(MediaType.IMAGE_PNG_VALUE))
                .andDo(print())
                .andExpect(status().isOk()).andReturn();
        byte[] response = mvcResponse.getResponse().getContentAsByteArray();

        //then
        assertNotNull(mvcResponse.getResponse());
        assertArrayEquals(expectedResult, response);
    }
}
