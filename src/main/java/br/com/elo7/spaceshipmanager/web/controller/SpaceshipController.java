package br.com.elo7.spaceshipmanager.web.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.elo7.spaceshipmanager.exception.model.ErrorDTO;
import br.com.elo7.spaceshipmanager.service.SpaceshipService;
import br.com.elo7.spaceshipmanager.web.model.SpaceshipDTO;
import br.com.elo7.spaceshipmanager.web.model.SpaceshipInputDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;


@RestController
@RequestMapping("/api/v1/spaceship")
@AllArgsConstructor
@Tag(name = "Spaceship")
public class SpaceshipController {
	  
	private final SpaceshipService spaceshipService;    
	
	
	@GetMapping()
	@Operation(summary = "Get Spaceships")
	@ApiResponses(value = {
	        @ApiResponse(responseCode = "200", description = "Success",
	                content = @Content(schema = @Schema(implementation = SpaceshipDTO.class)))})
	public ResponseEntity<List<SpaceshipDTO>> getSpaceships() {
	    return new ResponseEntity<>(spaceshipService.getSpaceships(), HttpStatus.OK);
	}
	
    @PostMapping()
    @Operation(summary = "Create")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success",
                    content = @Content(schema = @Schema(implementation = SpaceshipInputDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content(schema = @Schema(implementation = ErrorDTO.class)))})
    public ResponseEntity<List<SpaceshipDTO>> createNewPlanet(@Valid @RequestBody SpaceshipInputDTO spaceshipInputDTO) {
    	
        return new ResponseEntity<>(spaceshipService.
                positioningNewSpaceships(spaceshipInputDTO), HttpStatus.CREATED);
    }
    
	@GetMapping(value = "/planet/{planetId}")
	@Operation(summary = "Get Spaceships By Planet")
	@ApiResponses(value = {
	        @ApiResponse(responseCode = "200", description = "Success",
	                content = @Content(schema = @Schema(implementation = SpaceshipDTO.class)))})
	public ResponseEntity<List<SpaceshipDTO>> getSpaceshipByPlanet(@PathVariable Long planetId) {
	    return new ResponseEntity<>(spaceshipService.getSpaceshipsByPlanetId(planetId), HttpStatus.OK);
	}
    

}
