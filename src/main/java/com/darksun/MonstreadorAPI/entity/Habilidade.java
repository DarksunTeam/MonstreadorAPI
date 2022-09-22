package com.darksun.MonstreadorAPI.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Habilidade implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private Long id;

	private String  nome;
	private String  descricao;
	private Integer custo;

	@OneToMany
	@JoinTable( name = "habilidade_prerequisito", joinColumns = {
			@JoinColumn( name = "dependente_id", referencedColumnName = "id" ) }, inverseJoinColumns = {
			@JoinColumn( name = "requisito_id", referencedColumnName = "id" ) } )
	private List< Habilidade > preRequisitos;

	public void addPreRequisito( Habilidade habilidade ) {
		preRequisitos.add( habilidade );
	}

	public void removePreRequisito( Habilidade habilidade ) {
		preRequisitos.remove( habilidade );
	}
}
