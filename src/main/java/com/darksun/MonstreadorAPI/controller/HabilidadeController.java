package com.darksun.MonstreadorAPI.controller;

import com.darksun.MonstreadorAPI.entity.Habilidade;
import com.darksun.MonstreadorAPI.repository.AtaqueRepository;
import com.darksun.MonstreadorAPI.repository.HabilidadeRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping( value = "/api" )
@Api( value = "API REST Habilidades" )
@CrossOrigin( origins = "*" )
public class HabilidadeController {

	@Autowired
	HabilidadeRepository habilidadeRepository;

	@GetMapping( "/habilidades" )
	@ApiOperation( value = "Retorna todos os habilidades cadastrados na base" )
	public List< Habilidade > listaHabilidades( ) {
		return habilidadeRepository.findAll( );
	}

	@GetMapping( "/habilidade/{id}" )
	@ApiOperation( value = "Busca um habilidade na base pelo ID cadastrado" )
	public Optional< Habilidade > buscarHabilidade( @PathVariable( value = "id" ) Long id ) {
		return habilidadeRepository.findById( id );
	}

	@PostMapping( "/habilidade" )
	@ApiOperation( value = "Cadastra um novo habilidade na base" )
	public Habilidade salvaHabilidade( @RequestBody Habilidade habilidade ) {
		return habilidadeRepository.save( habilidade );
	}

	@DeleteMapping( "/habilidade" )
	@ApiOperation( value = "Apaga um habilidade da base" )
	public void deletaHabilidade( @RequestBody Habilidade habilidade ) {
		habilidadeRepository.delete( habilidade );
	}

	@PutMapping( "/habilidade" )
	@ApiOperation( value = "Altera informações de um habilidade já cadastrado na base" )
	public Habilidade atualizaHabilidade( @RequestBody Habilidade habilidade ) {
		return habilidadeRepository.save( habilidade );
	}
}
