package br.com.elo7.spaceshipmanager.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.elo7.spaceshipmanager.domain.Spaceship;

public interface SpaceshipRepository extends JpaRepository<Spaceship, Long> {
	
	Optional<Spaceship> findByXAndYAndPlanetId(Integer x, Integer y, Long planetId);
	
	List<Spaceship> findByPlanetId(Long planetId);
}
