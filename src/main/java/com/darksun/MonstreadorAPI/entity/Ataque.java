package com.darksun.MonstreadorAPI.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Ataque implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	private String  nome;
	private Integer acerto;
	private Integer quantidadeDados;
	private Integer tipoDado;
	private Integer incrementoDano;

	@Enumerated( EnumType.ORDINAL )
	private TipoDano tipoDano;

	@ManyToOne
	@JsonBackReference
	@JoinColumn( name = "MONSTRO" )
	private Monstro monstro;
}
