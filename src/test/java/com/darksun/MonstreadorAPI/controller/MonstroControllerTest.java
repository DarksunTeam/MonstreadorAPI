package com.darksun.MonstreadorAPI.controller;

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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class MonstroControllerTest {

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
	void listaMonstrosTest( ) {
		List< Monstro > monstros = TestUtils.generateListMonstro( );
		when( repository.findAll( ) ).thenReturn( monstros );
		ResponseEntity< List< Monstro > > response = controller.listaMonstros( );
		assertEquals( monstros, response.getBody( ) );
		assertEquals( HttpStatus.OK, response.getStatusCode( ) );
		verify( repository, times( 1 ) ).findAll( );
	}

	@Test
	void buscarMonstroTest( ) {
		Monstro monstro = TestUtils.generateMonstro( "Monstro", 1, 0 );
		when( service.buscaNaBase( 1L ) ).thenReturn( monstro );
		ResponseEntity< Monstro > response = controller.buscarMonstro( 1L );
		assertEquals( monstro, response.getBody( ) );
		assertEquals( HttpStatus.OK, response.getStatusCode( ) );
		verify( service, times( 1 ) ).buscaNaBase( any( ) );
	}

	@Test
	void salvaMonstroValidoTest( ) {
		Monstro monstro = TestUtils.generateMonstro( "Monstro", 1, 0 );
		when( repository.save( any( ) ) ).thenReturn( monstro );
		ResponseEntity< Monstro > response = controller.salvaMonstro( monstro );
		assertEquals( monstro, response.getBody( ) );
		assertEquals( HttpStatus.CREATED, response.getStatusCode( ) );
		verify( repository, times( 1 ) ).save( any( ) );
	}

	@Test
	void deletaMonstroSucessTest( ) {
		List< Monstro > monstros  = new ArrayList<>( );
		Monstro         esqueleto = TestUtils.generateMonstro( "Esqueleto", 1, 0 );
		Monstro         goblin    = TestUtils.generateMonstro( "Goblin", 1, 0 );
		monstros.add( esqueleto );
		monstros.remove( goblin );

		doNothing( ).when( repository ).deleteById( any( ) );
		monstros.remove( goblin );
		ResponseEntity< HttpStatus > response = controller.deletaMonstro( goblin );

		assertEquals( 1, monstros.size( ) );
		assertEquals( HttpStatus.NO_CONTENT, response.getStatusCode( ) );
		verify( repository, times( 1 ) ).delete( any( ) );
	}

	@Test
	void atualizaMonstroTest( ) {
		Monstro monstro = TestUtils.generateMonstro( "Monstro", 1, 0 );
		when( repository.findById( any( ) ) ).thenReturn( Optional.ofNullable( monstro ) );
		when( repository.save( any( ) ) ).thenReturn( monstro );
		ResponseEntity< Monstro > response = controller.atualizaMonstro( monstro );
		assertEquals( monstro, response.getBody( ) );
		assertEquals( HttpStatus.OK, response.getStatusCode( ) );
		verify( repository, times( 1 ) ).save( any( ) );
	}
}
