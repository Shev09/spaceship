package br.com.elo7.spaceshipmanager.service;

import br.com.elo7.spaceshipmanager.domain.Planet;
import br.com.elo7.spaceshipmanager.mapper.PlanetMapper;
import br.com.elo7.spaceshipmanager.repository.PlanetRepository;
import br.com.elo7.spaceshipmanager.web.model.NewPlanetDTO;
import br.com.elo7.spaceshipmanager.web.model.PlanetDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.OptimisticLockingFailureException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlanetServiceImplTest {

    @Mock
    private PlanetRepository mockRepository;
    @Mock
    private PlanetMapper mockMapper;

    private PlanetServiceImpl planetServiceImplUnderTest;

    @BeforeEach
    void setUp() {
        planetServiceImplUnderTest = new PlanetServiceImpl(mockRepository, mockMapper);
    }

    @Test
    void testGetPlanets() {
        // Setup
        final PlanetDTO planetDTO = new PlanetDTO();
        planetDTO.setId(0L);
        planetDTO.setWidth(0);
        planetDTO.setHeight(0);
        final List<PlanetDTO> expectedResult = List.of(planetDTO);
        when(mockRepository.findAll()).thenReturn(List.of(Planet.builder().build()));

        // Configure PlanetMapper.planetToPlanetDTOs(...).
        final PlanetDTO planetDTO1 = new PlanetDTO();
        planetDTO1.setId(0L);
        planetDTO1.setWidth(0);
        planetDTO1.setHeight(0);
        final List<PlanetDTO> planetDTOS = List.of(planetDTO1);
        when(mockMapper.planetToPlanetDTOs(any())).thenReturn(planetDTOS);

        // Run the test
        final List<PlanetDTO> result = planetServiceImplUnderTest.getPlanets();

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }
    
    @Test
    void testGetPlanets_PlanetRepositoryReturnsNoItems() {
        // Setup
        final PlanetDTO planetDTO = new PlanetDTO();
        planetDTO.setId(0L);
        planetDTO.setWidth(0);
        planetDTO.setHeight(0);
        final List<PlanetDTO> expectedResult = List.of(planetDTO);
        when(mockRepository.findAll()).thenReturn(Collections.emptyList());

        // Configure PlanetMapper.planetToPlanetDTOs(...).
        final PlanetDTO planetDTO1 = new PlanetDTO();
        planetDTO1.setId(0L);
        planetDTO1.setWidth(0);
        planetDTO1.setHeight(0);
        final List<PlanetDTO> planetDTOS = List.of(planetDTO1);
        when(mockMapper.planetToPlanetDTOs(any())).thenReturn(planetDTOS);

        // Run the test
        final List<PlanetDTO> result = planetServiceImplUnderTest.getPlanets();

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testGetPlanets_PlanetMapperReturnsNoItems() {
        // Setup
        when(mockRepository.findAll()).thenReturn(List.of(Planet.builder().build()));
        when(mockMapper.planetToPlanetDTOs(any())).thenReturn(Collections.emptyList());

        // Run the test
        final List<PlanetDTO> result = planetServiceImplUnderTest.getPlanets();

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    void testCreateNewPlanet() {
        // Setup
        final NewPlanetDTO newPlanetDTO = new NewPlanetDTO();
        newPlanetDTO.setWidth(0);
        newPlanetDTO.setHeight(0);

        final PlanetDTO expectedResult = new PlanetDTO();
        expectedResult.setId(0L);
        expectedResult.setWidth(0);
        expectedResult.setHeight(0);

        // Configure PlanetMapper.newPlanetDTOToPlanet(...).
        final NewPlanetDTO newPlanetDTO1 = new NewPlanetDTO();
        newPlanetDTO1.setWidth(0);
        newPlanetDTO1.setHeight(0);
        when(mockMapper.newPlanetDTOToPlanet(newPlanetDTO1)).thenReturn(Planet.builder().build());

        when(mockRepository.save(any(Planet.class))).thenReturn(Planet.builder().build());

        // Configure PlanetMapper.planetToPlanetDTO(...).
        final PlanetDTO planetDTO = new PlanetDTO();
        planetDTO.setId(0L);
        planetDTO.setWidth(0);
        planetDTO.setHeight(0);
        when(mockMapper.planetToPlanetDTO(any(Planet.class))).thenReturn(planetDTO);

        // Run the test
        final PlanetDTO result = planetServiceImplUnderTest.createNewPlanet(newPlanetDTO);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testCreateNewPlanet_PlanetRepositoryThrowsOptimisticLockingFailureException() {
        // Setup
        final NewPlanetDTO newPlanetDTO = new NewPlanetDTO();
        newPlanetDTO.setWidth(0);
        newPlanetDTO.setHeight(0);

        // Configure PlanetMapper.newPlanetDTOToPlanet(...).
        final NewPlanetDTO newPlanetDTO1 = new NewPlanetDTO();
        newPlanetDTO1.setWidth(0);
        newPlanetDTO1.setHeight(0);
        when(mockMapper.newPlanetDTOToPlanet(newPlanetDTO1)).thenReturn(Planet.builder().build());

        when(mockRepository.save(any(Planet.class))).thenThrow(OptimisticLockingFailureException.class);

        // Run the test
        assertThatThrownBy(() -> planetServiceImplUnderTest.createNewPlanet(newPlanetDTO))
                .isInstanceOf(OptimisticLockingFailureException.class);
    }

    @Test
    void testGetPlanetById() {
        // Setup
        when(mockRepository.findById(1L)).thenReturn(Optional.of(Planet.builder().id(1L).width(1).height(1).build()));

        // Run the test
        final Optional<Planet> result = planetServiceImplUnderTest.getPlanetById(1L);

        // Verify the results
        
        assertEquals(result.get().getId(), 1L);
    }

    @Test
    void testGetPlanetById_PlanetRepositoryReturnsAbsent() {
        // Setup
        when(mockRepository.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        final Optional<Planet> result = planetServiceImplUnderTest.getPlanetById(0L);

        // Verify the results
        assertThat(result).isEmpty();
    }
}
