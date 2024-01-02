package br.com.elo7.spaceshipmanager.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.com.elo7.spaceshipmanager.domain.Spaceship;
import br.com.elo7.spaceshipmanager.web.model.SpaceshipDTO;

@Mapper
public interface SpaceshipMapper {

	@Mapping(target = "planetId", source = "planet.id")
	SpaceshipDTO spaceshipToSpaceshipDTO(Spaceship spaceship);
	List<SpaceshipDTO> spaceshipToSpaceshipDTOs(List<Spaceship> spaceships);
	

}
