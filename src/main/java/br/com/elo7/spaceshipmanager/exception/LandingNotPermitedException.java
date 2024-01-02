package br.com.elo7.spaceshipmanager.exception;

import br.com.elo7.spaceshipmanager.domain.Spaceship;

public class LandingNotPermitedException  extends RuntimeException {

	public LandingNotPermitedException(Spaceship spaceship) {
		super(String.format("The spaceship cannot land at position X: %s,Y: %s on planetId: %s, as there is already a spaceship landed. Try new position or a new Planet.",
				spaceship.getX(), spaceship.getY(), spaceship.getPlanet().getId()));
	}
}
