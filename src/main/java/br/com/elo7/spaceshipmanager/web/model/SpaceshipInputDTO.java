package br.com.elo7.spaceshipmanager.web.model;

import java.util.List;

import lombok.Data;

@Data
public class SpaceshipInputDTO {
	
	private Long planetId;
	
	List<NewSpaceshipDTO> spaceships;
	
}
