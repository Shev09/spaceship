package br.com.elo7.spaceshipmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.elo7.spaceshipmanager.domain.Planet;

public interface PlanetRepository extends JpaRepository<Planet, Long> {
	
}
