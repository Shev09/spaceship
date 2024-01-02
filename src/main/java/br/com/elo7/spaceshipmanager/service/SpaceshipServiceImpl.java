package br.com.elo7.spaceshipmanager.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.elo7.spaceshipmanager.domain.Planet;
import br.com.elo7.spaceshipmanager.domain.Spaceship;
import br.com.elo7.spaceshipmanager.enumeration.Direction;
import br.com.elo7.spaceshipmanager.exception.LandingNotPermitedException;
import br.com.elo7.spaceshipmanager.exception.OutsidePlanetException;
import br.com.elo7.spaceshipmanager.exception.ResourceNotFoundException;
import br.com.elo7.spaceshipmanager.mapper.SpaceshipMapper;
import br.com.elo7.spaceshipmanager.repository.SpaceshipRepository;
import br.com.elo7.spaceshipmanager.web.model.NewSpaceshipDTO;
import br.com.elo7.spaceshipmanager.web.model.SpaceshipDTO;
import br.com.elo7.spaceshipmanager.web.model.SpaceshipInputDTO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SpaceshipServiceImpl implements SpaceshipService {

	private final SpaceshipRepository repository;
	private final SpaceshipMapper mapper;
	private final PlanetService planetService;

	@Override
	public List<SpaceshipDTO> getSpaceships() {
		return mapper.spaceshipToSpaceshipDTOs(repository.findAll());
	}

    @Override
    public List<SpaceshipDTO> positioningNewSpaceships(SpaceshipInputDTO spaceshipInputDTO) {
    	
    	Planet planet = planetService.getPlanetById(spaceshipInputDTO.getPlanetId()).orElseThrow(
    			() -> new ResourceNotFoundException(String.format("Planet id: %s", spaceshipInputDTO.getPlanetId())));
    	
        for (NewSpaceshipDTO newSpaceshipDTO : spaceshipInputDTO.getSpaceships()) {
            Spaceship spaceship = createSpaceship(newSpaceshipDTO, planet);
            processCommands(spaceship, newSpaceshipDTO.getCommands());
            updateSpaceship(spaceship);
        }

        return getSpaceshipsByPlanetId(planet.getId());
    }
    
	@Override
	public List<SpaceshipDTO> getSpaceshipsByPlanetId(Long planetId) {
		return mapper.spaceshipToSpaceshipDTOs(repository.findByPlanetId(planetId));
	}

    private Spaceship createSpaceship(NewSpaceshipDTO newSpaceshipDTO, Planet planet) {
        return Spaceship.builder()
        		.x(newSpaceshipDTO.getIntial_pos_x())
        		.y(newSpaceshipDTO.getIntial_pos_y())
        		.direction(Direction.valueOf(newSpaceshipDTO.getDirection()))
        		.planet(planet)
        		.build();
    }

    private void processCommands(Spaceship spaceship, String commands) {
        for (char command : commands.toCharArray()) {
            processCommand(spaceship, command);
        }
    }

    private void processCommand(Spaceship spaceship, char command) {
        switch (command) {
            case 'M':
                moveForward(spaceship);
                break;
            case 'L':
                turnLeft(spaceship);
                break;
            case 'R':
                turnRight(spaceship);
                break;
        }
    }

    private void moveForward(Spaceship spaceship) {
        switch (spaceship.getDirection()) {
            case N:
                spaceship.setY(spaceship.getY() + 1);
                break;
            case E:
                spaceship.setX(spaceship.getX() + 1);
                break;
            case S:
                spaceship.setY(spaceship.getY() - 1);
                break;
            case W:
                spaceship.setX(spaceship.getX() - 1);
                break;
        }
    }

    private void turnLeft(Spaceship spaceship) {
        spaceship.setDirection(spaceship.getDirection().turnLeft());
    }

    private void turnRight(Spaceship spaceship) {
        spaceship.setDirection(spaceship.getDirection().turnRight());
    }

    private void updateSpaceship(Spaceship spaceship) {
    	
    	boolean isExceedGreaterThanLimits = spaceship.getX() > spaceship.getPlanet().getWidth() || spaceship.getY() > spaceship.getPlanet().getHeight(); 
    	boolean isNegativeCoordinate = spaceship.getX() < 0 || spaceship.getY() < 0;
    	
		if (isExceedGreaterThanLimits || isNegativeCoordinate) {
			throw new OutsidePlanetException(spaceship);
		}
    		    	
    	if(repository.findByXAndYAndPlanetId(spaceship.getX(), spaceship.getY(), spaceship.getPlanet().getId()).isPresent())
    		throw new LandingNotPermitedException(spaceship);
    	
        repository.save(spaceship);
    }
}
