package com.darksun.MonstreadorAPI.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Monstro implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	private String nome;

	private Integer forca;
	private Integer destreza;
	private Integer constituicao;
	private Integer inteligencia;
	private Integer sabedoria;
	private Integer carisma;

	private Integer pontosDeVida;
	private Integer pontosDeMana;
	private Integer iniciativa;
	private Integer percepcao;
	private Integer defesa;
	private Integer fortitude;
	private Integer reflexo;
	private Integer vontade;

	private Double velocidadeAndar;
	private Double velocidadeEscalar;
	private Double velocidadeNadar;
	private Double velocidadeVoar;

	private Integer nd;

	@JsonManagedReference
	@OneToMany( mappedBy = "monstro", cascade = CascadeType.ALL )
	private List< Ataque > ataques = new ArrayList< Ataque >( );

	public void addAtaque( Ataque ataque ) {
		ataques.add( ataque );
	}

	public void removeAtaque( Ataque ataque ) {
		ataques.remove( ataque );
	}
}
