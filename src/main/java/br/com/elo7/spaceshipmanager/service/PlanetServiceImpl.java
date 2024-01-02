package br.com.elo7.spaceshipmanager.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.elo7.spaceshipmanager.domain.Planet;
import br.com.elo7.spaceshipmanager.mapper.PlanetMapper;
import br.com.elo7.spaceshipmanager.repository.PlanetRepository;
import br.com.elo7.spaceshipmanager.web.model.NewPlanetDTO;
import br.com.elo7.spaceshipmanager.web.model.PlanetDTO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PlanetServiceImpl implements PlanetService {

	private final PlanetRepository repository;
	private final PlanetMapper mapper;

	@Override
	public List<PlanetDTO> getPlanets() {
		return mapper.planetToPlanetDTOs(repository.findAll());
	}

	@Override
	public PlanetDTO createNewPlanet(NewPlanetDTO newPlanetDTO) {
		Planet planet = repository.save(mapper.newPlanetDTOToPlanet(newPlanetDTO)); 
		return mapper.planetToPlanetDTO(planet);
	}

	@Override
	public Optional<Planet> getPlanetById(Long id) {
		return repository.findById(id);
	}
}
