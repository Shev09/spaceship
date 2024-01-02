package br.com.elo7.spaceshipmanager.service;

import java.util.List;
import java.util.Optional;

import br.com.elo7.spaceshipmanager.domain.Planet;
import br.com.elo7.spaceshipmanager.web.model.NewPlanetDTO;
import br.com.elo7.spaceshipmanager.web.model.PlanetDTO;

public interface PlanetService {

	List<PlanetDTO> getPlanets();

	PlanetDTO createNewPlanet(NewPlanetDTO newPlanetDTO);
	
	Optional<Planet> getPlanetById(Long id);
	
}