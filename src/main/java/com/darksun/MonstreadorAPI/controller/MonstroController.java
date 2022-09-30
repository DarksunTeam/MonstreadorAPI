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
import java.util.Optional;

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
	public ResponseEntity< Monstro > salvaMonstro( @RequestBody Monstro monstro ) {
		if ( !monstroService.validaMonstro( monstro ) ) {
			return new ResponseEntity<>( null, HttpStatus.FORBIDDEN );
		}
		return new ResponseEntity<>( monstroRepository.save( monstro ), HttpStatus.OK );
	}

	@DeleteMapping( "/monstro" )
	@ApiOperation( value = "Apaga um monstro da base" )
	public void deletaMonstro( @RequestBody Monstro monstro ) {
		monstroRepository.delete( monstro );
	}

	@PutMapping( "/monstro" )
	@ApiOperation( value = "Altera informações de um monstro já cadastrado na base" )
	public ResponseEntity< Monstro > atualizaMonstro( @RequestBody Monstro monstro ) {
		if ( !monstroService.validaMonstro( monstro ) ) {
			return new ResponseEntity<>( null, HttpStatus.FORBIDDEN );
		}
		return new ResponseEntity<>( monstroRepository.save( monstro ), HttpStatus.OK );
	}
}
