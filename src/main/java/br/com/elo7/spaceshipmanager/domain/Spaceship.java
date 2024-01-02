package br.com.elo7.spaceshipmanager.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import br.com.elo7.spaceshipmanager.enumeration.Direction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.With;

@Builder
@Getter
@Setter
@Entity
@Table(name = "spaceship")
@NoArgsConstructor
@AllArgsConstructor
public class Spaceship {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "direction")
	private Direction direction;
	
	@Column(name = "x")
	private Integer x;
	
	@Column(name = "y")
	private Integer y;
	
    @ManyToOne
    @JoinColumn(name = "planet_id")
    @NotNull
    @With
    private Planet planet;

}