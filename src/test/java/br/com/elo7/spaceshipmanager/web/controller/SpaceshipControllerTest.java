package br.com.elo7.spaceshipmanager.web.controller;

import br.com.elo7.spaceshipmanager.service.SpaceshipService;
import br.com.elo7.spaceshipmanager.web.model.NewSpaceshipDTO;
import br.com.elo7.spaceshipmanager.web.model.PlanetDTO;
import br.com.elo7.spaceshipmanager.web.model.SpaceshipDTO;
import br.com.elo7.spaceshipmanager.web.model.SpaceshipInputDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(SpaceshipController.class)
class SpaceshipControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SpaceshipService mockSpaceshipService;

    @Test
    void testGetSpaceships() throws Exception {
        // Setup
        // Configure SpaceshipService.getSpaceships(...).
        final SpaceshipDTO spaceshipDTO = new SpaceshipDTO();
        spaceshipDTO.setId(0L);
        spaceshipDTO.setX(0);
        spaceshipDTO.setY(0);
        spaceshipDTO.setDirection("N");
        spaceshipDTO.setPlanetId(0L);
        final List<SpaceshipDTO> spaceshipDTOS = List.of(spaceshipDTO);
        when(mockSpaceshipService.getSpaceships()).thenReturn(spaceshipDTOS);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/api/v1/spaceship")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        
        // Parse the actual response content as a List<SpaceshipDTO>
        ObjectMapper objectMapper = new ObjectMapper();
        List<SpaceshipDTO> actualResponse = objectMapper.readValue(response.getContentAsString(), new TypeReference<>() {});

        
        assertThat(actualResponse).isEqualTo(spaceshipDTOS);
    }

    @Test
    void testGetSpaceships_SpaceshipServiceReturnsNoItems() throws Exception {
        // Setup
        when(mockSpaceshipService.getSpaceships()).thenReturn(Collections.emptyList());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/api/v1/spaceship")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("[]");
    }

    @Test
    void testCreateNewPlanet() throws Exception {
        // Setup
        final SpaceshipDTO spaceshipDTO = new SpaceshipDTO();
        spaceshipDTO.setId(0L);
        spaceshipDTO.setX(0);
        spaceshipDTO.setY(0);
        spaceshipDTO.setDirection("N");
        spaceshipDTO.setPlanetId(0L);
        final List<SpaceshipDTO> spaceshipDTOS = List.of(spaceshipDTO);

        final SpaceshipInputDTO spaceshipInputDTO = new SpaceshipInputDTO();
        spaceshipInputDTO.setPlanetId(0L);

        final NewSpaceshipDTO newSpaceshipDTO = new NewSpaceshipDTO();
        newSpaceshipDTO.setIntial_pos_x(0);
        newSpaceshipDTO.setIntial_pos_y(0);
        newSpaceshipDTO.setDirection("N");

        spaceshipInputDTO.setSpaceships(List.of(newSpaceshipDTO));

        // Convert spaceshipInputDTO to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonContent = objectMapper.writeValueAsString(spaceshipInputDTO);

        when(mockSpaceshipService.positioningNewSpaceships(spaceshipInputDTO)).thenReturn(spaceshipDTOS);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/api/v1/spaceship")
                        .content(jsonContent).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    void testCreateNewPlanet_SpaceshipServiceReturnsNoItems() throws Exception {
        // Setup
        // Configure SpaceshipService.positioningNewSpaceships(...).
        final SpaceshipInputDTO spaceshipInputDTO = new SpaceshipInputDTO();
        spaceshipInputDTO.setPlanetId(0L);
        final NewSpaceshipDTO newSpaceshipDTO = new NewSpaceshipDTO();
        newSpaceshipDTO.setIntial_pos_x(0);
        newSpaceshipDTO.setIntial_pos_y(0);
        newSpaceshipDTO.setDirection("N");
        spaceshipInputDTO.setSpaceships(List.of(newSpaceshipDTO));
        when(mockSpaceshipService.positioningNewSpaceships(spaceshipInputDTO)).thenReturn(Collections.emptyList());

        // Convert spaceshipInputDTO to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonContent = objectMapper.writeValueAsString(spaceshipInputDTO);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/api/v1/spaceship")
                        .content(jsonContent).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getContentAsString()).isEqualTo("[]");
    }

    @Test
    void testGetSpaceshipByPlanet() throws Exception {
        // Setup
        // Configure SpaceshipService.getSpaceshipsByPlanetId(...).
        final SpaceshipDTO spaceshipDTO = new SpaceshipDTO();
        spaceshipDTO.setId(0L);
        spaceshipDTO.setX(0);
        spaceshipDTO.setY(0);
        spaceshipDTO.setDirection("N");
        spaceshipDTO.setPlanetId(0L);
        final List<SpaceshipDTO> spaceshipDTOS = List.of(spaceshipDTO);
        when(mockSpaceshipService.getSpaceshipsByPlanetId(0L)).thenReturn(spaceshipDTOS);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/api/v1/spaceship/planet/{planetId}", 0)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        
        // Parse the actual response content as a List<SpaceshipDTO>
        ObjectMapper objectMapper = new ObjectMapper();
        List<SpaceshipDTO> actualResponse = objectMapper.readValue(response.getContentAsString(), new TypeReference<>() {});

        
        assertThat(actualResponse).isEqualTo(spaceshipDTOS);
    }

    @Test
    void testGetSpaceshipByPlanet_SpaceshipServiceReturnsNoItems() throws Exception {
        // Setup
        when(mockSpaceshipService.getSpaceshipsByPlanetId(0L)).thenReturn(Collections.emptyList());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/api/v1/spaceship/planet/{planetId}", 0)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("[]");
    }
}
