package com.darksun.MonstreadorAPI.controller;

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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class HabilidadeControllerTest {

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
	void listaHabilidadesTest( ) {
		List< Habilidade > habilidades = TestUtils.generateListHabilidade( );
		when( repository.findAll( ) ).thenReturn( habilidades );
		ResponseEntity< List< Habilidade > > response = controller.listaHabilidades( );
		assertEquals( habilidades, response.getBody( ) );
		assertEquals( HttpStatus.OK, response.getStatusCode( ) );
		verify( repository, times( 1 ) ).findAll( );
	}

	@Test
	void buscarHabilidadeTest( ) {
		Habilidade habilidade = TestUtils.generateHabilidade( 1L, "Habilidade" );
		when( service.buscaNaBase( 1L ) ).thenReturn( habilidade );
		ResponseEntity< Habilidade > response = controller.buscarHabilidade( 1L );
		assertEquals( habilidade, response.getBody( ) );
		assertEquals( HttpStatus.OK, response.getStatusCode( ) );
		verify( service, times( 1 ) ).buscaNaBase( any( ) );
	}

	@Test
	void salvaHabilidadeValidoTest( ) {
		Habilidade habilidade = TestUtils.generateHabilidade( 1l, "Habilidade" );
		when( repository.save( any( ) ) ).thenReturn( habilidade );
		ResponseEntity< Habilidade > response = controller.salvaHabilidade( habilidade );
		assertEquals( habilidade, response.getBody( ) );
		assertEquals( HttpStatus.CREATED, response.getStatusCode( ) );
		verify( repository, times( 1 ) ).save( any( ) );
	}

	@Test
	void deletaHabilidadeSucessTest( ) {
		List< Habilidade > habilidades = new ArrayList<>( );
		Habilidade         trespassar  = TestUtils.generateHabilidade( 1L, "Trespassar" );
		Habilidade         vitalidade  = TestUtils.generateHabilidade( 2L, "Vitalidade" );
		habilidades.add( trespassar );
		habilidades.remove( vitalidade );

		doNothing( ).when( repository ).deleteById( any( ) );
		habilidades.remove( vitalidade );
		ResponseEntity< HttpStatus > response = controller.deletaHabilidade( vitalidade );

		assertEquals( 1, habilidades.size( ) );
		assertEquals( HttpStatus.NO_CONTENT, response.getStatusCode( ) );
		verify( repository, times( 1 ) ).delete( any( ) );
	}

	@Test
	void atualizaHabilidadeTest( ) {
		Habilidade habilidade = TestUtils.generateHabilidade( 1L, "Habilidade" );
		when( repository.findById( any( ) ) ).thenReturn( Optional.ofNullable( habilidade ) );
		when( repository.save( any( ) ) ).thenReturn( habilidade );
		ResponseEntity< Habilidade > response = controller.atualizaHabilidade( habilidade );
		assertEquals( habilidade, response.getBody( ) );
		assertEquals( HttpStatus.OK, response.getStatusCode( ) );
		verify( repository, times( 1 ) ).save( any( ) );
	}
}
