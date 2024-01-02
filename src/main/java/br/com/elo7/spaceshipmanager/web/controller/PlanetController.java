package br.com.elo7.spaceshipmanager.web.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.elo7.spaceshipmanager.exception.model.ErrorDTO;
import br.com.elo7.spaceshipmanager.service.PlanetService;
import br.com.elo7.spaceshipmanager.web.model.NewPlanetDTO;
import br.com.elo7.spaceshipmanager.web.model.PlanetDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;


@RestController
@RequestMapping("/api/v1/planet")
@AllArgsConstructor
@Tag(name = "Planet")
public class PlanetController {
	  
	private final PlanetService planetService;    
	
	
	@GetMapping()
	@Operation(summary = "Get Planets")
	@ApiResponses(value = {
	        @ApiResponse(responseCode = "200", description = "Success",
	                content = @Content(schema = @Schema(implementation = PlanetDTO.class)))})
	public ResponseEntity<List<PlanetDTO>> getPlanets() {
	    return new ResponseEntity<>(planetService.getPlanets(), HttpStatus.OK);
	}
	
    @PostMapping()
    @Operation(summary = "Create")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Success",
                    content = @Content(schema = @Schema(implementation = NewPlanetDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content(schema = @Schema(implementation = ErrorDTO.class)))})
    public ResponseEntity<PlanetDTO> createNewPlanet(@Valid @RequestBody NewPlanetDTO newPlanetDTO) {
    	
        return new ResponseEntity<>(planetService
                .createNewPlanet(newPlanetDTO), HttpStatus.CREATED);
    }
    

}
