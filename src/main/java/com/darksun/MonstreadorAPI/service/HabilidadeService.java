package com.darksun.MonstreadorAPI.service;

import com.darksun.MonstreadorAPI.entity.Habilidade;
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
}
