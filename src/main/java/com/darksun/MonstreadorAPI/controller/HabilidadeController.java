package com.darksun.MonstreadorAPI.controller;

import com.darksun.MonstreadorAPI.entity.Habilidade;
import com.darksun.MonstreadorAPI.repository.HabilidadeRepository;
import com.darksun.MonstreadorAPI.service.HabilidadeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping( value = "/api" )
@Api( value = "API REST Habilidades" )
@CrossOrigin( origins = "*" )
public class HabilidadeController {

	@Autowired
	HabilidadeRepository habilidadeRepository;

	@Autowired
	HabilidadeService habilidadeService;

	@GetMapping( "/habilidades" )
	@ApiOperation( value = "Retorna todos os habilidades cadastrados na base" )
	public ResponseEntity< List< Habilidade > > listaHabilidades( ) {
		return new ResponseEntity<>( habilidadeRepository.findAll( ), HttpStatus.OK );
	}

	@GetMapping( "/habilidade/{id}" )
	@ApiOperation( value = "Busca um habilidade na base pelo ID cadastrado" )
	public ResponseEntity< Habilidade > buscarHabilidade( @PathVariable( value = "id" ) Long id ) {
		return new ResponseEntity<>( habilidadeService.buscaNaBase( id ), HttpStatus.OK );
	}

	@PostMapping( "/habilidade" )
	@ApiOperation( value = "Cadastra um novo habilidade na base" )
	public ResponseEntity< Habilidade > salvaHabilidade( @RequestBody Habilidade habilidade ) {
		return new ResponseEntity<>( habilidadeRepository.save( habilidade ), HttpStatus.CREATED );
	}

	@DeleteMapping( "/habilidade" )
	@ApiOperation( value = "Apaga um habilidade da base" )
	public ResponseEntity< HttpStatus > deletaHabilidade( @RequestBody Habilidade habilidade ) {
		habilidadeService.buscaNaBase( habilidade.getId( ) );
		habilidadeRepository.delete( habilidade );
		return new ResponseEntity<>( HttpStatus.NO_CONTENT );
	}

	@PutMapping( "/habilidade" )
	@ApiOperation( value = "Altera informações de um habilidade já cadastrado na base" )
	public ResponseEntity< Habilidade > atualizaHabilidade( @RequestBody Habilidade habilidade ) {
		habilidadeService.buscaNaBase( habilidade.getId( ) );
		return new ResponseEntity<>( habilidadeRepository.save( habilidade ), HttpStatus.OK );
	}
}
