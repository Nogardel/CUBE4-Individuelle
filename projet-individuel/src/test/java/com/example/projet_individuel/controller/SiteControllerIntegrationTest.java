package com.example.projet_individuel.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.example.projet_individuel.model.Site;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.example.projet_individuel.service.SiteApiService;

@SpringBootTest
@AutoConfigureMockMvc
class SiteControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SiteApiService siteApiService;

    @Test
    void getAllSites_ShouldReturnSiteList() throws Exception {
        mockMvc.perform(get("/api/sites"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[*].ville").exists());
    }

    @Test
    void getSiteById_WhenSiteExists_ShouldReturnSite() throws Exception {
        mockMvc.perform(get("/api/sites/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.ville").exists());
    }

    @Test
    void getSiteById_WhenSiteDoesNotExist_ShouldReturn404() throws Exception {
        mockMvc.perform(get("/api/sites/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createSite_WithValidData_ShouldReturnCreatedSite() throws Exception {
        Site newSite = Site.builder()
                .ville("Marseille")
                .build();
        String siteJson = objectMapper.writeValueAsString(newSite);

        mockMvc.perform(post("/api/sites")
                .contentType(MediaType.APPLICATION_JSON)
                .content(siteJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.ville").value("Marseille"));
    }

    @Test
    void updateSite_WithValidData_ShouldReturnUpdatedSite() throws Exception {
        Site updatedSite = Site.builder()
                .id(1L)
                .ville("Paris")
                .build();
        String siteJson = objectMapper.writeValueAsString(updatedSite);

        mockMvc.perform(put("/api/sites/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(siteJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ville").value("Paris"));
    }

    @Test
    void deleteSite_WhenSiteExists_ShouldReturn204() throws Exception {
        Site site = Site.builder()
                .ville("Lyon")
                .build();
        String siteJson = objectMapper.writeValueAsString(site);

        MvcResult result = mockMvc.perform(post("/api/sites")
                .contentType(MediaType.APPLICATION_JSON)
                .content(siteJson))
                .andExpect(status().isCreated())
                .andReturn();

        Integer id = JsonPath.parse(result.getResponse().getContentAsString()).read("$.id", Integer.class);

        mockMvc.perform(delete("/api/sites/" + id))
                .andExpect(status().isNoContent());
    }
}