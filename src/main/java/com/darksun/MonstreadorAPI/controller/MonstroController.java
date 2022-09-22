package com.darksun.MonstreadorAPI.controller;

import com.darksun.MonstreadorAPI.entity.Monstro;
import com.darksun.MonstreadorAPI.repository.MonstroRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping( value = "/api" )
@Api( value = "API REST Monstros" )
@CrossOrigin( origins = "*" )
public class MonstroController {

	@Autowired
	MonstroRepository monstroRepository;

	@GetMapping( "/monstros" )
	@ApiOperation( value = "Retorna todos os monstros cadastrados na base" )
	public List< Monstro > listaMonstros( ) {
		return monstroRepository.findAll( );
	}

	@GetMapping( "/monstro/{id}" )
	@ApiOperation( value = "Busca um monstro na base pelo ID cadastrado" )
	public Optional< Monstro > buscarMonstro( @PathVariable( value = "id" ) Long id ) {
		return monstroRepository.findById( id );
	}

	@PostMapping( "/monstro" )
	@ApiOperation( value = "Cadastra um novo monstro na base" )
	public Monstro salvaMonstro( @RequestBody Monstro monstro ) {
		return monstroRepository.save( monstro );
	}

	@DeleteMapping( "/monstro" )
	@ApiOperation( value = "Apaga um monstro da base" )
	public void deletaMonstro( @RequestBody Monstro monstro ) {
		monstroRepository.delete( monstro );
	}

	@PutMapping( "/monstro" )
	@ApiOperation( value = "Altera informações de um monstro já cadastrado na base" )
	public Monstro atualizaMonstro( @RequestBody Monstro monstro ) {
		return monstroRepository.save( monstro );
	}
}
