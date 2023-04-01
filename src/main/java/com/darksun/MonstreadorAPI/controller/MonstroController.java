package com.darksun.MonstreadorAPI.controller;

import com.darksun.MonstreadorAPI.entity.Monstro;
import com.darksun.MonstreadorAPI.repository.MonstroRepository;
import com.darksun.MonstreadorAPI.service.MonstroService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping( value = "/api" )
@Api( value = "API REST Monstros" )
@CrossOrigin( origins = "*" )
public class MonstroController {

	@Autowired
	MonstroRepository monstroRepository;

	@Autowired
	MonstroService monstroService;

	@GetMapping( "/monstros" )
	@ApiOperation( value = "Retorna todos os monstros cadastrados na base" )
	public ResponseEntity< List< Monstro > > listaMonstros( ) {
		return new ResponseEntity<>( monstroRepository.findAll( ), HttpStatus.OK );
	}

	@GetMapping( "/monstros/{id}" )
	@ApiOperation( value = "Busca um monstro na base pelo ID cadastrado" )
	public ResponseEntity< Monstro > buscarMonstro( @PathVariable( value = "id" ) Long id ) {
		return new ResponseEntity<>( monstroService.buscaNaBase( id ), HttpStatus.OK );
	}

	@PostMapping( "/monstro" )
	@ApiOperation( value = "Cadastra um novo monstro na base" )
	public ResponseEntity< Monstro > salvaMonstro( @RequestBody Monstro monstro ) {
		monstroService.validaMonstro( monstro );
		return new ResponseEntity<>( monstroRepository.save( monstro ), HttpStatus.CREATED );
	}

	@DeleteMapping( "/monstros" )
	@ApiOperation( value = "Apaga um monstro da base" )
	public ResponseEntity< HttpStatus > deletaMonstro( @RequestBody Monstro monstro ) {
		monstroService.buscaNaBase( monstro.getId( ) );
		monstroRepository.delete( monstro );
		return new ResponseEntity<>( HttpStatus.NO_CONTENT );
	}

	@PutMapping( "/monstros" )
	@ApiOperation( value = "Altera informações de um monstro já cadastrado na base" )
	public ResponseEntity< Monstro > atualizaMonstro( @RequestBody Monstro monstro ) {
		monstroService.buscaNaBase( monstro.getId( ) );
		monstroService.validaMonstro( monstro );
		return new ResponseEntity<>( monstroRepository.save( monstro ), HttpStatus.OK );
	}
}
