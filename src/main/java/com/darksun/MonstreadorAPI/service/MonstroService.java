package com.darksun.MonstreadorAPI.service;

import com.darksun.MonstreadorAPI.entity.Habilidade;
import com.darksun.MonstreadorAPI.entity.Monstro;
import com.darksun.MonstreadorAPI.exception.InvalidPrerequisitesException;
import com.darksun.MonstreadorAPI.exception.ResourceNotFoundException;
import com.darksun.MonstreadorAPI.repository.MonstroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MonstroService {

	@Autowired
	MonstroRepository repository;

	@Autowired
	HabilidadeService habilidadeService;

	public Monstro buscaNaBase( Long id ) {
		return repository.findById( id )
						 .orElseThrow( ( ) -> new ResourceNotFoundException(
								 "Monstro não encontrado para o ID: " + id ) );
	}

	public void validaMonstro( Monstro monstro ) {
		validaPrerequisitosHabilidades( monstro.getHabilidades( ) );
	}

	public void validaPrerequisitosHabilidades( List< Habilidade > habilidades ) {
		for ( Habilidade habilidade : habilidades ) {
			Habilidade dbHabilidade = habilidadeService.buscaNaBase( habilidade.getId( ) );
			for ( Habilidade preRequisito : dbHabilidade.getPreRequisitos( ) ) {
				if ( !habilidades.contains( preRequisito ) ) {
					throw new InvalidPrerequisitesException(
							"Monstro não cumpre o pré-requisito " + preRequisito.getNome( )
									+ " da habilidade " + habilidade.getNome( ) );
				}
			}
		}
	}
}
