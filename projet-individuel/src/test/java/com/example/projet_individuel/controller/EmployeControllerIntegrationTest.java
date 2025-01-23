package com.example.projet_individuel.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.containsString;

@SpringBootTest
@AutoConfigureMockMvc
class EmployeControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getAllEmployes_ShouldReturnEmployeList() throws Exception {
        mockMvc.perform(get("/api/employes"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[*].nom").exists());
    }

    @Test
    void getEmployeById_WhenNotFound_ShouldReturn404() throws Exception {
        mockMvc.perform(get("/api/employes/999999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createEmploye_WithInvalidData_ShouldReturn400() throws Exception {
        mockMvc.perform(post("/api/employes")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isBadRequest());
    }
}