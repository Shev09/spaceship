package br.com.elo7.spaceshipmanager.web.model;

import lombok.Data;

@Data
public class NewSpaceshipDTO {
		
	private int intial_pos_x;
	
	private int intial_pos_y;
	
	private String direction;
	
	private String commands;
		
}
