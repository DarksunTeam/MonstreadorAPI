package com.darksun.MonstreadorAPI.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serial;
import java.io.Serializable;

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
}
