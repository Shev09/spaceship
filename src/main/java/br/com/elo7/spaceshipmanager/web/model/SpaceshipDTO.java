package br.com.elo7.spaceshipmanager.web.model;

import lombok.Data;

@Data
public class SpaceshipDTO {
	
	private Long id;
	
	private int x;
	
	private int y;
	
	private String direction;
	
	private Long planetId;
	
}
