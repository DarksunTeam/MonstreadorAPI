package com.darksun.MonstreadorAPI.controller;

import com.darksun.MonstreadorAPI.entity.Monstro;
import com.darksun.MonstreadorAPI.repository.MonstroRepository;
import com.darksun.MonstreadorAPI.service.MonstroService;
import com.darksun.MonstreadorAPI.util.TestUtils;
import org.hibernate.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class MonstroControllerTest {

	private static final String OBJETO_NAO_ENCONTRADO        = "Objeto não encontrado";
	private static final String OBJETO_NAO_ENCONTRADO_NESTED = OBJETO_NAO_ENCONTRADO
			+ "; nested exception is javax.persistence.EntityNotFoundException: "
			+ OBJETO_NAO_ENCONTRADO;

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
		List< Monstro > response = controller.listaMonstros( );
		assertEquals( monstros, response );
		verify( repository, times( 1 ) ).findAll( );
	}

	@Test
	void buscarMonstroTest( ) {
		Monstro monstro = TestUtils.generateMonstro( "Monstro", 1, 0 );
		when( repository.findById( 1L ) ).thenReturn( Optional.ofNullable( monstro ) );
		Optional< Monstro > response = controller.buscarMonstro( 1L );
		assertEquals( monstro, response.get( ) );
		verify( repository, times( 1 ) ).findById( any( ) );
	}

	@Test
	void salvaMonstroValidoTest( ) {
		Monstro monstro = TestUtils.generateMonstro( "Monstro", 1, 0 );
		when( repository.save( any( ) ) ).thenReturn( monstro );
		when( service.validaMonstro( any( ) ) ).thenReturn( true );
		Monstro response = controller.salvaMonstro( monstro ).getBody( );
		assertEquals( monstro, response );
		verify( repository, times( 1 ) ).save( any( ) );
	}

	@Test
	void salvaMonstroInvalidoTest( ) {
		Monstro monstro = TestUtils.generateMonstro( "Monstro", 1, 0 );
		when( repository.save( any( ) ) ).thenReturn( monstro );
		when( service.validaMonstro( any( ) ) ).thenReturn( false );
		Monstro response = controller.salvaMonstro( monstro ).getBody( );
		assertEquals( null, response );
		verify( repository, times( 0 ) ).save( any( ) );
	}

	@Test
	void salvaMonstroHabilidadeNaoEncontradaTest( ) {
		Monstro monstro = TestUtils.generateMonstro( "Monstro", 1, 0 );
		when( service.validaMonstro( any( ) ) ).thenReturn( true );
		when( repository.save( any( ) ) ).thenThrow( new JpaObjectRetrievalFailureException(
				new EntityNotFoundException( OBJETO_NAO_ENCONTRADO ) ) );
		try {
			controller.salvaMonstro( monstro );
		} catch ( Exception ex ) {
			assertEquals( JpaObjectRetrievalFailureException.class, ex.getClass( ) );
			assertEquals( OBJETO_NAO_ENCONTRADO_NESTED, ex.getMessage( ) );

		}
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
		controller.deletaMonstro( goblin );

		assertEquals( 1, monstros.size( ) );
		verify( repository, times( 1 ) ).delete( any( ) );
	}

	@Test
	void deletaMonstroNaoEncontradoTest( ) {
		when( repository.findById( anyLong( ) ) ).thenThrow(
				new ObjectNotFoundException( "Teste Unitário", OBJETO_NAO_ENCONTRADO ) );
		try {
			controller.deletaMonstro( TestUtils.generateMonstro( "Goblin", 1, 0 ) );
		} catch ( Exception ex ) {
			assertEquals( ObjectNotFoundException.class, ex.getClass( ) );
			assertEquals( OBJETO_NAO_ENCONTRADO, ex.getMessage( ) );
		}
		verify( repository, times( 1 ) ).delete( any( ) );
	}

	@Test
	void atualizaMonstroTest( ) {
		Monstro monstro = TestUtils.generateMonstro( "Monstro", 1, 0 );
		when( repository.save( any( ) ) ).thenReturn( monstro );
		when( service.validaMonstro( any( ) ) ).thenReturn( true );
		Monstro response = controller.atualizaMonstro( monstro ).getBody( );
		assertEquals( monstro, response );
		verify( repository, times( 1 ) ).save( any( ) );
	}

	@Test
	void atualizaMonstroInvalidoTest( ) {
		Monstro monstro = TestUtils.generateMonstro( "Monstro", 1, 0 );
		when( repository.save( any( ) ) ).thenReturn( monstro );
		when( service.validaMonstro( any( ) ) ).thenReturn( false );
		Monstro response = controller.atualizaMonstro( monstro ).getBody( );
		assertEquals( null, response );
		verify( repository, times( 0 ) ).save( any( ) );
	}

	@Test
	void atualizaMonstroNaoEncontradoTest( ) {
		when( service.validaMonstro( any( ) ) ).thenReturn( true );
		when( repository.findById( anyLong( ) ) ).thenThrow(
				new ObjectNotFoundException( "Teste Unitário", OBJETO_NAO_ENCONTRADO ) );
		try {
			controller.atualizaMonstro( TestUtils.generateMonstro( "Goblin", 1, 0 ) );
		} catch ( Exception ex ) {
			assertEquals( ObjectNotFoundException.class, ex.getClass( ) );
			assertEquals( OBJETO_NAO_ENCONTRADO, ex.getMessage( ) );
		}
		verify( repository, times( 1 ) ).save( any( ) );
	}
}
