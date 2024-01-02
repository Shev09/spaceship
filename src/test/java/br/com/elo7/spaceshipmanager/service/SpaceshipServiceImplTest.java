package br.com.elo7.spaceshipmanager.service;

import br.com.elo7.spaceshipmanager.domain.Planet;
import br.com.elo7.spaceshipmanager.domain.Spaceship;
import br.com.elo7.spaceshipmanager.enumeration.Direction;
import br.com.elo7.spaceshipmanager.exception.LandingNotPermitedException;
import br.com.elo7.spaceshipmanager.exception.ResourceNotFoundException;
import br.com.elo7.spaceshipmanager.mapper.SpaceshipMapper;
import br.com.elo7.spaceshipmanager.repository.SpaceshipRepository;
import br.com.elo7.spaceshipmanager.web.model.NewSpaceshipDTO;
import br.com.elo7.spaceshipmanager.web.model.SpaceshipDTO;
import br.com.elo7.spaceshipmanager.web.model.SpaceshipInputDTO;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SpaceshipServiceImplTest {

    @Mock
    private SpaceshipRepository mockRepository;
    @Mock
    private SpaceshipMapper mockMapper;
    @Mock
    private PlanetService mockPlanetService;

    private SpaceshipServiceImpl spaceshipServiceImplUnderTest;

    @BeforeEach
    void setUp() {
        spaceshipServiceImplUnderTest = new SpaceshipServiceImpl(mockRepository, mockMapper, mockPlanetService);
    }

    @Test
    void testGetSpaceships() {
        // Setup
        final SpaceshipDTO spaceshipDTO = new SpaceshipDTO();
        spaceshipDTO.setId(0L);
        spaceshipDTO.setX(0);
        spaceshipDTO.setY(0);
        spaceshipDTO.setDirection("direction");
        spaceshipDTO.setPlanetId(0L);
        final List<SpaceshipDTO> expectedResult = List.of(spaceshipDTO);

        // Configure SpaceshipRepository.findAll(...).
        final List<Spaceship> spaceships = List.of(Spaceship.builder()
                .direction(Direction.N)
                .x(0)
                .y(0)
                .planet(Planet.builder()
                        .id(0L)
                        .width(0)
                        .height(0)
                        .build())
                .build());
        when(mockRepository.findAll()).thenReturn(spaceships);

        // Configure SpaceshipMapper.spaceshipToSpaceshipDTOs(...).
        final SpaceshipDTO spaceshipDTO1 = new SpaceshipDTO();
        spaceshipDTO1.setId(0L);
        spaceshipDTO1.setX(0);
        spaceshipDTO1.setY(0);
        spaceshipDTO1.setDirection("direction");
        spaceshipDTO1.setPlanetId(0L);
        final List<SpaceshipDTO> spaceshipDTOS = List.of(spaceshipDTO1);
        when(mockMapper.spaceshipToSpaceshipDTOs(any())).thenReturn(spaceshipDTOS);

        // Run the test
        final List<SpaceshipDTO> result = spaceshipServiceImplUnderTest.getSpaceships();

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testGetSpaceships_SpaceshipRepositoryReturnsNoItems() {
        // Setup
        final SpaceshipDTO spaceshipDTO = new SpaceshipDTO();
        spaceshipDTO.setId(0L);
        spaceshipDTO.setX(0);
        spaceshipDTO.setY(0);
        spaceshipDTO.setDirection("direction");
        spaceshipDTO.setPlanetId(0L);
        final List<SpaceshipDTO> expectedResult = List.of(spaceshipDTO);
        when(mockRepository.findAll()).thenReturn(Collections.emptyList());

        // Configure SpaceshipMapper.spaceshipToSpaceshipDTOs(...).
        final SpaceshipDTO spaceshipDTO1 = new SpaceshipDTO();
        spaceshipDTO1.setId(0L);
        spaceshipDTO1.setX(0);
        spaceshipDTO1.setY(0);
        spaceshipDTO1.setDirection("direction");
        spaceshipDTO1.setPlanetId(0L);
        final List<SpaceshipDTO> spaceshipDTOS = List.of(spaceshipDTO1);
        when(mockMapper.spaceshipToSpaceshipDTOs(any())).thenReturn(spaceshipDTOS);

        // Run the test
        final List<SpaceshipDTO> result = spaceshipServiceImplUnderTest.getSpaceships();

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testGetSpaceships_SpaceshipMapperReturnsNoItems() {
        // Setup
        // Configure SpaceshipRepository.findAll(...).
        final List<Spaceship> spaceships = List.of(Spaceship.builder()
                .direction(Direction.N)
                .x(0)
                .y(0)
                .planet(Planet.builder()
                        .id(0L)
                        .width(0)
                        .height(0)
                        .build())
                .build());
        when(mockRepository.findAll()).thenReturn(spaceships);

        when(mockMapper.spaceshipToSpaceshipDTOs(any())).thenReturn(Collections.emptyList());

        // Run the test
        final List<SpaceshipDTO> result = spaceshipServiceImplUnderTest.getSpaceships();

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    void testPositioningNewSpaceships_ThrowsLandingNotPermitedException() {
        // Setup
        final SpaceshipInputDTO spaceshipInputDTO = new SpaceshipInputDTO();
        spaceshipInputDTO.setPlanetId(0L);
        final NewSpaceshipDTO newSpaceshipDTO = new NewSpaceshipDTO();
        newSpaceshipDTO.setIntial_pos_x(0);
        newSpaceshipDTO.setIntial_pos_y(0);
        newSpaceshipDTO.setDirection("N");
        newSpaceshipDTO.setCommands("commands");
        spaceshipInputDTO.setSpaceships(List.of(newSpaceshipDTO));

        // Configure PlanetService.getPlanetById(...).
        final Optional<Planet> planet = Optional.of(Planet.builder()
                .id(0L)
                .width(0)
                .height(0)
                .build());
        when(mockPlanetService.getPlanetById(0L)).thenReturn(planet);

        // Configure SpaceshipRepository.findByXAndYAndPlanetId(...).
        final Optional<Spaceship> spaceship = Optional.of(Spaceship.builder()
                .direction(Direction.N)
                .x(0)
                .y(0)
                .planet(Planet.builder()
                        .id(0L)
                        .width(0)
                        .height(0)
                        .build())
                .build());
        when(mockRepository.findByXAndYAndPlanetId(0, 0, 0L)).thenReturn(spaceship);

        // Run the test
        assertThatThrownBy(
                () -> spaceshipServiceImplUnderTest.positioningNewSpaceships(spaceshipInputDTO))
                .isInstanceOf(LandingNotPermitedException.class);
    }

    @Test
    void testPositioningNewSpaceships_PlanetServiceReturnsAbsent() {
        // Setup
        final SpaceshipInputDTO spaceshipInputDTO = new SpaceshipInputDTO();
        spaceshipInputDTO.setPlanetId(0L);
        final NewSpaceshipDTO newSpaceshipDTO = new NewSpaceshipDTO();
        newSpaceshipDTO.setIntial_pos_x(0);
        newSpaceshipDTO.setIntial_pos_y(0);
        newSpaceshipDTO.setDirection("direction");
        newSpaceshipDTO.setCommands("commands");
        spaceshipInputDTO.setSpaceships(List.of(newSpaceshipDTO));

        when(mockPlanetService.getPlanetById(0L)).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(
                () -> spaceshipServiceImplUnderTest.positioningNewSpaceships(spaceshipInputDTO))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void testPositioningNewSpaceships_SpaceshipRepositorySaveThrowsOptimisticLockingFailureException() {
        // Setup
        final SpaceshipInputDTO spaceshipInputDTO = new SpaceshipInputDTO();
        spaceshipInputDTO.setPlanetId(0L);
        final NewSpaceshipDTO newSpaceshipDTO = new NewSpaceshipDTO();
        newSpaceshipDTO.setIntial_pos_x(0);
        newSpaceshipDTO.setIntial_pos_y(0);
        newSpaceshipDTO.setDirection("N");
        newSpaceshipDTO.setCommands("commands");
        spaceshipInputDTO.setSpaceships(List.of(newSpaceshipDTO));

        // Configure PlanetService.getPlanetById(...).
        final Optional<Planet> planet = Optional.of(Planet.builder()
                .id(0L)
                .width(0)
                .height(0)
                .build());
        when(mockPlanetService.getPlanetById(0L)).thenReturn(planet);

        when(mockRepository.findByXAndYAndPlanetId(0, 0, 0L)).thenReturn(Optional.empty());
        when(mockRepository.save(any(Spaceship.class))).thenThrow(OptimisticLockingFailureException.class);

        // Run the test
        assertThatThrownBy(
                () -> spaceshipServiceImplUnderTest.positioningNewSpaceships(spaceshipInputDTO))
                .isInstanceOf(OptimisticLockingFailureException.class);
    }

    @Test
    void testPositioningNewSpaceships_SpaceshipMapperReturnsNoItems() {
        // Setup
        final SpaceshipInputDTO spaceshipInputDTO = new SpaceshipInputDTO();
        spaceshipInputDTO.setPlanetId(0L);
        final NewSpaceshipDTO newSpaceshipDTO = new NewSpaceshipDTO();
        newSpaceshipDTO.setIntial_pos_x(0);
        newSpaceshipDTO.setIntial_pos_y(0);
        newSpaceshipDTO.setDirection("N");
        newSpaceshipDTO.setCommands("commands");
        spaceshipInputDTO.setSpaceships(List.of(newSpaceshipDTO));

        // Configure PlanetService.getPlanetById(...).
        final Optional<Planet> planet = Optional.of(Planet.builder()
                .id(0L)
                .width(0)
                .height(0)
                .build());
        when(mockPlanetService.getPlanetById(0L)).thenReturn(planet);

        when(mockRepository.findByXAndYAndPlanetId(0, 0, 0L)).thenReturn(Optional.empty());

        // Configure SpaceshipRepository.findByPlanetId(...).
        final List<Spaceship> spaceships = List.of(Spaceship.builder()
                .direction(Direction.N)
                .x(0)
                .y(0)
                .planet(Planet.builder()
                        .id(0L)
                        .width(0)
                        .height(0)
                        .build())
                .build());
        when(mockRepository.findByPlanetId(0L)).thenReturn(spaceships);

        when(mockMapper.spaceshipToSpaceshipDTOs(any())).thenReturn(Collections.emptyList());

        // Run the test
        final List<SpaceshipDTO> result = spaceshipServiceImplUnderTest.positioningNewSpaceships(spaceshipInputDTO);

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
        verify(mockRepository).save(any(Spaceship.class));
    }

    @Test
    void testGetSpaceshipsByPlanetId() {
        // Setup
        final SpaceshipDTO spaceshipDTO = new SpaceshipDTO();
        spaceshipDTO.setId(0L);
        spaceshipDTO.setX(0);
        spaceshipDTO.setY(0);
        spaceshipDTO.setDirection("direction");
        spaceshipDTO.setPlanetId(0L);
        final List<SpaceshipDTO> expectedResult = List.of(spaceshipDTO);

        // Configure SpaceshipRepository.findByPlanetId(...).
        final List<Spaceship> spaceships = List.of(Spaceship.builder()
                .direction(Direction.N)
                .x(0)
                .y(0)
                .planet(Planet.builder()
                        .id(0L)
                        .width(0)
                        .height(0)
                        .build())
                .build());
        when(mockRepository.findByPlanetId(0L)).thenReturn(spaceships);

        // Configure SpaceshipMapper.spaceshipToSpaceshipDTOs(...).
        final SpaceshipDTO spaceshipDTO1 = new SpaceshipDTO();
        spaceshipDTO1.setId(0L);
        spaceshipDTO1.setX(0);
        spaceshipDTO1.setY(0);
        spaceshipDTO1.setDirection("direction");
        spaceshipDTO1.setPlanetId(0L);
        final List<SpaceshipDTO> spaceshipDTOS = List.of(spaceshipDTO1);
        when(mockMapper.spaceshipToSpaceshipDTOs(any())).thenReturn(spaceshipDTOS);

        // Run the test
        final List<SpaceshipDTO> result = spaceshipServiceImplUnderTest.getSpaceshipsByPlanetId(0L);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testGetSpaceshipsByPlanetId_SpaceshipRepositoryReturnsNoItems() {
        // Setup
        final SpaceshipDTO spaceshipDTO = new SpaceshipDTO();
        spaceshipDTO.setId(0L);
        spaceshipDTO.setX(0);
        spaceshipDTO.setY(0);
        spaceshipDTO.setDirection("direction");
        spaceshipDTO.setPlanetId(0L);
        final List<SpaceshipDTO> expectedResult = List.of(spaceshipDTO);
        when(mockRepository.findByPlanetId(0L)).thenReturn(Collections.emptyList());

        // Configure SpaceshipMapper.spaceshipToSpaceshipDTOs(...).
        final SpaceshipDTO spaceshipDTO1 = new SpaceshipDTO();
        spaceshipDTO1.setId(0L);
        spaceshipDTO1.setX(0);
        spaceshipDTO1.setY(0);
        spaceshipDTO1.setDirection("direction");
        spaceshipDTO1.setPlanetId(0L);
        final List<SpaceshipDTO> spaceshipDTOS = List.of(spaceshipDTO1);
        when(mockMapper.spaceshipToSpaceshipDTOs(any())).thenReturn(spaceshipDTOS);

        // Run the test
        final List<SpaceshipDTO> result = spaceshipServiceImplUnderTest.getSpaceshipsByPlanetId(0L);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testGetSpaceshipsByPlanetId_SpaceshipMapperReturnsNoItems() {
        // Setup
        // Configure SpaceshipRepository.findByPlanetId(...).
        final List<Spaceship> spaceships = List.of(Spaceship.builder()
                .direction(Direction.N)
                .x(0)
                .y(0)
                .planet(Planet.builder()
                        .id(0L)
                        .width(0)
                        .height(0)
                        .build())
                .build());
        when(mockRepository.findByPlanetId(0L)).thenReturn(spaceships);

        when(mockMapper.spaceshipToSpaceshipDTOs(any())).thenReturn(Collections.emptyList());

        // Run the test
        final List<SpaceshipDTO> result = spaceshipServiceImplUnderTest.getSpaceshipsByPlanetId(0L);

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }
}
