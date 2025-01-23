package com.example.projet_individuel.controller;

import com.example.projet_individuel.model.ServiceEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import com.jayway.jsonpath.JsonPath;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ServiceControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllServices_ShouldReturnServiceList() throws Exception {
        mockMvc.perform(get("/api/services"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[*].nom").exists());
    }

    @Test
    void getServiceById_WhenServiceExists_ShouldReturnService() throws Exception {
        mockMvc.perform(get("/api/services/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.nom").exists());
    }

    @Test
    void getServiceById_WhenServiceDoesNotExist_ShouldReturn404() throws Exception {
        mockMvc.perform(get("/api/services/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createService_WithValidData_ShouldReturnCreatedService() throws Exception {
        ServiceEntity newService = ServiceEntity.builder()
                .nom("Comptabilité")
                .build();
        String serviceJson = objectMapper.writeValueAsString(newService);

        mockMvc.perform(post("/api/services")
                .contentType(MediaType.APPLICATION_JSON)
                .content(serviceJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nom").value("Comptabilité"));
    }

    @Test
    void updateService_WithValidData_ShouldReturnUpdatedService() throws Exception {
        ServiceEntity updatedService = ServiceEntity.builder()
                .nom("RH")
                .build();
        String serviceJson = objectMapper.writeValueAsString(updatedService);

        mockMvc.perform(put("/api/services/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(serviceJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nom").value("RH"));
    }

    @Test
    void deleteService_WhenServiceExists_ShouldReturn204() throws Exception {
        ServiceEntity service = ServiceEntity.builder()
                .nom("Service Test")
                .build();
        String serviceJson = objectMapper.writeValueAsString(service);

        MvcResult result = mockMvc.perform(post("/api/services")
                .contentType(MediaType.APPLICATION_JSON)
                .content(serviceJson))
                .andExpect(status().isCreated())
                .andReturn();

        Integer id = JsonPath.parse(result.getResponse().getContentAsString()).read("$.id", Integer.class);

        mockMvc.perform(delete("/api/services/" + id))
                .andExpect(status().isNoContent());
    }
}