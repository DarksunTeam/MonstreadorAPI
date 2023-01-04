package com.darksun.MonstreadorAPI.service;

import com.darksun.MonstreadorAPI.entity.Habilidade;
import com.darksun.MonstreadorAPI.exception.InvalidPrerequisitesException;
import com.darksun.MonstreadorAPI.exception.ResourceNotFoundException;
import com.darksun.MonstreadorAPI.repository.HabilidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HabilidadeService {

	@Autowired
	HabilidadeRepository repository;

	public Habilidade buscaNaBase( Long id ) {
		return repository.findById( id )
						 .orElseThrow( ( ) -> new ResourceNotFoundException(
								 "Habilidade não encontrada para o ID: " + id ) );
	}

	public void validaHabilidade( Habilidade habilidade ) {
		validaPrerequisitosHabilidades( habilidade );
	}

	public void validaPrerequisitosHabilidades( Habilidade habilidade ) {
		try {
			for ( Habilidade preRequisito : habilidade.getPreRequisitos( ) ) {
				buscaNaBase( preRequisito.getId( ) );
			}
		} catch ( ResourceNotFoundException ex ) {
			throw new InvalidPrerequisitesException( "A habilidade " + habilidade.getNome( )
															 + " possui pré-requisitos não cadastrados." );
		}
	}
}
