package br.com.elo7.spaceshipmanager.exception;

import br.com.elo7.spaceshipmanager.domain.Spaceship;

public class OutsidePlanetException  extends RuntimeException {

	public OutsidePlanetException(Spaceship spaceship) {
		super(String.format("The spaceship cannot land at position X: %s,Y: %s on planetId: %s, the limits of the planet have been exceeded. Try new position or a new Planet.",
				spaceship.getX(), spaceship.getY(), spaceship.getPlanet().getId()));
	}
}
