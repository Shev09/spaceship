package br.com.elo7.spaceshipmanager.web.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.elo7.spaceshipmanager.service.PlanetService;
import br.com.elo7.spaceshipmanager.web.model.NewPlanetDTO;
import br.com.elo7.spaceshipmanager.web.model.PlanetDTO;

@WebMvcTest(PlanetController.class)
class PlanetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlanetService mockPlanetService;
    
    @Test
    void testGetPlanets() throws Exception {
        // Setup
        // Configure PlanetService.getPlanets(...).
        final PlanetDTO planetDTO = new PlanetDTO();
        planetDTO.setId(0L);
        planetDTO.setWidth(0);
        planetDTO.setHeight(0);
        final List<PlanetDTO> planetDTOS = List.of(planetDTO);
        when(mockPlanetService.getPlanets()).thenReturn(planetDTOS);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/api/v1/planet")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        // Parse the actual response content as a List<PlanetDTO>
        ObjectMapper objectMapper = new ObjectMapper();
        List<PlanetDTO> actualResponse = objectMapper.readValue(response.getContentAsString(), new TypeReference<>() {});

        // Compare the lists of PlanetDTO
        assertThat(actualResponse).isEqualTo(planetDTOS);
    }

    @Test
    void testGetPlanets_PlanetServiceReturnsNoItems() throws Exception {
        // Setup
        when(mockPlanetService.getPlanets()).thenReturn(Collections.emptyList());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/api/v1/planet")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("[]");
    }
    
    @Test
    void testCreateNewPlanet() throws Exception {
        // Setup
        // Configure PlanetService.createNewPlanet(...).
        final PlanetDTO planetDTO = new PlanetDTO();
        planetDTO.setId(0L);
        planetDTO.setWidth(0);
        planetDTO.setHeight(0);
        
        final NewPlanetDTO newPlanetDTO = new NewPlanetDTO();
        newPlanetDTO.setWidth(0);
        newPlanetDTO.setHeight(0);
        
        final String requestContent = "{\"width\": 0, \"height\": 0}";

        when(mockPlanetService.createNewPlanet(any())).thenReturn(planetDTO);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/api/v1/planet")
                        .content(requestContent)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getContentAsString()).isEqualTo("{\"id\":0,\"width\":0,\"height\":0}");
    }
}
