package br.com.elo7.spaceshipmanager.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.com.elo7.spaceshipmanager.domain.Planet;
import br.com.elo7.spaceshipmanager.web.model.NewPlanetDTO;
import br.com.elo7.spaceshipmanager.web.model.PlanetDTO;

@Mapper
public interface PlanetMapper {

	PlanetDTO planetToPlanetDTO(Planet planet);
	List<PlanetDTO> planetToPlanetDTOs(List<Planet> planets);
	
    @Mapping(target = "id", ignore = true)
	Planet newPlanetDTOToPlanet(NewPlanetDTO newPlanetDTO);
	List<Planet> newPlanetDTOToPlanets(List<PlanetDTO> newPlanetDTOs);
	

}
