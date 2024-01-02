package br.com.elo7.spaceshipmanager.service;

import java.util.List;

import br.com.elo7.spaceshipmanager.web.model.SpaceshipDTO;
import br.com.elo7.spaceshipmanager.web.model.SpaceshipInputDTO;

public interface SpaceshipService {

	List<SpaceshipDTO> getSpaceships();
	
	List<SpaceshipDTO> getSpaceshipsByPlanetId(Long planetId);

	List<SpaceshipDTO> positioningNewSpaceships(SpaceshipInputDTO spaceshipInputDTO);
	
}