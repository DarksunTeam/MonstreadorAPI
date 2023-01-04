package com.darksun.MonstreadorAPI.exception;

import com.darksun.MonstreadorAPI.controller.MonstroController;
import com.darksun.MonstreadorAPI.entity.Monstro;
import com.darksun.MonstreadorAPI.repository.MonstroRepository;
import com.darksun.MonstreadorAPI.service.MonstroService;
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

public class MonstroExceptionHandlerTest {

	private static final String OBJETO_NAO_ENCONTRADO = "Objeto não encontrado";
	private static final String ENDPOINT_MONSTRO      = "uri=/api/monstro";

	@InjectMocks
	private RestExceptionHandler handler;

	@InjectMocks
	private MonstroController controller;

	@Mock
	private MonstroRepository repository;

	@Mock
	private MonstroService service;

	@BeforeEach
	void setUp( ) {
		MockitoAnnotations.openMocks( this );
	}

	@Test
	void salvaMonstroInvalidoTest( ) {
		Monstro monstro = TestUtils.generateGenericMonstro( );
		when( repository.save( any( ) ) ).thenReturn( monstro );
		doThrow( new InvalidPrerequisitesException( OBJETO_NAO_ENCONTRADO ) ).when( service )
																			 .validaMonstro(
																					 any( ) );
		ResponseEntity< ErrorMessage > response = null;
		try {
			controller.salvaMonstro( monstro );
		} catch ( Exception ex ) {
			assertThat( ex ).isInstanceOf( InvalidPrerequisitesException.class )
							.hasMessage( OBJETO_NAO_ENCONTRADO );
			response = handler.handle( ex, HttpStatus.BAD_REQUEST, ENDPOINT_MONSTRO );
		}
		assertEquals( HttpStatus.BAD_REQUEST, response.getStatusCode( ) );
		verify( repository, times( 0 ) ).save( any( ) );
	}

	@Test
	void salvaMonstroHabilidadeNaoEncontradaTest( ) {
		Monstro monstro = TestUtils.generateGenericMonstro( );
		when( repository.save( any( ) ) ).thenReturn( monstro );
		doThrow( new ResourceNotFoundException( OBJETO_NAO_ENCONTRADO ) ).when( service )
																		 .validaMonstro( any( ) );
		ResponseEntity< ErrorMessage > response = null;
		try {
			controller.salvaMonstro( monstro );
		} catch ( Exception ex ) {
			assertThat( ex ).isInstanceOf( ResourceNotFoundException.class )
							.hasMessage( OBJETO_NAO_ENCONTRADO );
			response = handler.handle( ex, HttpStatus.BAD_REQUEST, ENDPOINT_MONSTRO );
		}
		assertEquals( HttpStatus.BAD_REQUEST, response.getStatusCode( ) );
		verify( repository, times( 0 ) ).save( any( ) );
	}

	@Test
	void deletaMonstroNaoEncontradoTest( ) {
		when( service.buscaNaBase( anyLong( ) ) ).thenThrow(
				new ResourceNotFoundException( OBJETO_NAO_ENCONTRADO ) );
		ResponseEntity< ErrorMessage > response = new ResponseEntity<>(
				HttpStatus.INTERNAL_SERVER_ERROR );
		try {
			controller.deletaMonstro( TestUtils.generateMonstro( "Goblin", 1, 0 ) );
		} catch ( Exception ex ) {
			assertEquals( ResourceNotFoundException.class, ex.getClass( ) );
			assertEquals( OBJETO_NAO_ENCONTRADO, ex.getMessage( ) );
			response = handler.handle( ex, HttpStatus.NOT_FOUND, ENDPOINT_MONSTRO );
		}
		assertEquals( HttpStatus.NOT_FOUND, response.getStatusCode( ) );
		verify( repository, times( 0 ) ).delete( any( ) );
	}

	@Test
	void atualizaMonstroInvalidoTest( ) {
		Monstro monstro = TestUtils.generateGenericMonstro( );
		when( repository.save( any( ) ) ).thenReturn( monstro );
		doThrow( new ResourceNotFoundException( OBJETO_NAO_ENCONTRADO ) ).when( service )
																		 .validaMonstro( any( ) );
		ResponseEntity< ErrorMessage > response = null;
		try {
			controller.atualizaMonstro( monstro ).getBody( );
		} catch ( Exception ex ) {
			assertEquals( ResourceNotFoundException.class, ex.getClass( ) );
			assertEquals( OBJETO_NAO_ENCONTRADO, ex.getMessage( ) );
			response = handler.handle( ex, HttpStatus.NOT_FOUND, ENDPOINT_MONSTRO );
		}
		assertEquals( HttpStatus.NOT_FOUND, response.getStatusCode( ) );
		verify( repository, times( 0 ) ).save( any( ) );
	}

	@Test
	void atualizaMonstroNaoEncontradoTest( ) {
		Monstro monstro = TestUtils.generateGenericMonstro( );
		when( repository.save( any( ) ) ).thenReturn( monstro );
		doThrow( new ResourceNotFoundException( OBJETO_NAO_ENCONTRADO ) ).when( service )
																		 .validaMonstro( any( ) );
		ResponseEntity< ErrorMessage > response = null;
		try {
			controller.atualizaMonstro( monstro );
		} catch ( Exception ex ) {
			assertThat( ex ).isInstanceOf( ResourceNotFoundException.class )
							.hasMessage( OBJETO_NAO_ENCONTRADO );
			response = handler.handle( ex, HttpStatus.BAD_REQUEST, ENDPOINT_MONSTRO );
		}
		assertEquals( HttpStatus.BAD_REQUEST, response.getStatusCode( ) );
		verify( repository, times( 0 ) ).save( any( ) );
	}
}
