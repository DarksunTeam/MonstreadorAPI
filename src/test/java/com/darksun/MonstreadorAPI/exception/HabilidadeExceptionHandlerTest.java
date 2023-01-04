package com.darksun.MonstreadorAPI.exception;

import com.darksun.MonstreadorAPI.controller.HabilidadeController;
import com.darksun.MonstreadorAPI.entity.Habilidade;
import com.darksun.MonstreadorAPI.repository.HabilidadeRepository;
import com.darksun.MonstreadorAPI.service.HabilidadeService;
import com.darksun.MonstreadorAPI.util.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class HabilidadeExceptionHandlerTest {

	private static final String OBJETO_NAO_ENCONTRADO = "Objeto não encontrado";
	private static final String ENDPOINT_MONSTRO      = "uri=/api/habilidade";

	@InjectMocks
	private RestExceptionHandler handler;

	@InjectMocks
	private HabilidadeController controller;

	@Mock
	private HabilidadeRepository repository;

	@Mock
	private HabilidadeService service;

	@BeforeEach
	void setUp( ) {
		MockitoAnnotations.openMocks( this );
	}

	@Test
	void salvaHabilidadePrerequisitoNaoEncontradoTest( ) {
		Habilidade habilidade = TestUtils.generateGenericHabilidade( );
		when( repository.save( any( ) ) ).thenReturn( habilidade );
		doThrow( new ResourceNotFoundException( OBJETO_NAO_ENCONTRADO ) ).when( service )
																		 .validaHabilidade(
																				 any( ) );
		ResponseEntity< ErrorMessage > response = null;
		try {
			controller.salvaHabilidade( habilidade );
		} catch ( Exception ex ) {
			assertThat( ex ).isInstanceOf( ResourceNotFoundException.class )
							.hasMessage( OBJETO_NAO_ENCONTRADO );
			response = handler.handle( ex, HttpStatus.BAD_REQUEST, ENDPOINT_MONSTRO );
		}
		assertEquals( HttpStatus.BAD_REQUEST, response.getStatusCode( ) );
		verify( repository, times( 0 ) ).save( any( ) );
	}

	@Test
	void deletaHabilidadeNaoEncontradoTest( ) {
		when( service.buscaNaBase( anyLong( ) ) ).thenThrow(
				new ResourceNotFoundException( OBJETO_NAO_ENCONTRADO ) );
		ResponseEntity< ErrorMessage > response = new ResponseEntity<>(
				HttpStatus.INTERNAL_SERVER_ERROR );
		try {
			controller.deletaHabilidade( TestUtils.generateHabilidade( 1L, "Goblin" ) );
		} catch ( Exception ex ) {
			assertEquals( ResourceNotFoundException.class, ex.getClass( ) );
			assertEquals( OBJETO_NAO_ENCONTRADO, ex.getMessage( ) );
			response = handler.handle( ex, HttpStatus.NOT_FOUND, ENDPOINT_MONSTRO );
		}
		assertEquals( HttpStatus.NOT_FOUND, response.getStatusCode( ) );
		verify( repository, times( 0 ) ).delete( any( ) );
	}

	@Test
	void atualizaHabilidadePrerequisitoNaoEncontradoTest( ) {
		Habilidade habilidade = TestUtils.generateGenericHabilidade( );
		when( repository.save( any( ) ) ).thenReturn( habilidade );
		doThrow( new ResourceNotFoundException( OBJETO_NAO_ENCONTRADO ) ).when( service )
																		 .validaHabilidade(
																				 any( ) );
		ResponseEntity< ErrorMessage > response = null;
		try {
			controller.atualizaHabilidade( habilidade ).getBody( );
		} catch ( Exception ex ) {
			assertEquals( ResourceNotFoundException.class, ex.getClass( ) );
			assertEquals( OBJETO_NAO_ENCONTRADO, ex.getMessage( ) );
			response = handler.handle( ex, HttpStatus.NOT_FOUND, ENDPOINT_MONSTRO );
		}
		assertEquals( HttpStatus.NOT_FOUND, response.getStatusCode( ) );
		verify( repository, times( 0 ) ).save( any( ) );
	}

	@Test
	void atualizaHabilidadeNaoEncontradoTest( ) {
		Habilidade habilidade = TestUtils.generateGenericHabilidade( );
		when( repository.save( any( ) ) ).thenReturn( habilidade );
		doThrow( new ResourceNotFoundException( OBJETO_NAO_ENCONTRADO ) ).when( service )
																		 .validaHabilidade(
																				 any( ) );
		ResponseEntity< ErrorMessage > response = null;
		try {
			controller.atualizaHabilidade( habilidade );
		} catch ( Exception ex ) {
			assertThat( ex ).isInstanceOf( ResourceNotFoundException.class )
							.hasMessage( OBJETO_NAO_ENCONTRADO );
			response = handler.handle( ex, HttpStatus.BAD_REQUEST, ENDPOINT_MONSTRO );
		}
		assertEquals( HttpStatus.BAD_REQUEST, response.getStatusCode( ) );
		verify( repository, times( 0 ) ).save( any( ) );
	}
}
