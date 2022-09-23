package com.darksun.MonstreadorAPI.controller;

import com.darksun.MonstreadorAPI.entity.Monstro;
import com.darksun.MonstreadorAPI.repository.MonstroRepository;
import com.darksun.MonstreadorAPI.util.TestUtils;
import org.hibernate.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class MonstroControllerTest {

	private static final String OBJETO_NAO_ENCONTRADO = "Objeto não encontrado";

	@InjectMocks
	private MonstroController controller;

	@Mock
	private MonstroRepository repository;

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
	}

	@Test
	void buscarMonstroTest( ) {
		Monstro monstro = TestUtils.generateMonstro( "Monstro", 1, 0 );
		when( repository.findById( 1L ) ).thenReturn( Optional.ofNullable( monstro ) );
		Optional< Monstro > response = controller.buscarMonstro( 1L );
		assertEquals( monstro, response.get( ) );
	}

	@Test
	void salvaMonstroTest( ) {
		Monstro monstro = TestUtils.generateMonstro( "Monstro", 1, 0 );
		when( repository.save( any( ) ) ).thenReturn( monstro );
		Monstro response = controller.salvaMonstro( monstro );
		assertEquals( monstro, response );
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
	void deletaMonstroWhenObjectNotFoundTest( ) {
		when( repository.findById( anyLong( ) ) ).thenThrow(
				new ObjectNotFoundException( "Teste Unitário", OBJETO_NAO_ENCONTRADO ) );
		try {
			controller.deletaMonstro( TestUtils.generateMonstro( "Goblin", 1, 0 ) );
		} catch ( Exception ex ) {
			assertEquals( ObjectNotFoundException.class, ex.getClass( ) );
			assertEquals( OBJETO_NAO_ENCONTRADO, ex.getMessage( ) );
		}
	}

	@Test
	void atualizaMonstroTest( ) {
		Monstro monstro = TestUtils.generateMonstro( "Monstro", 1, 0 );
		when( repository.save( any( ) ) ).thenReturn( monstro );
		Monstro response = controller.atualizaMonstro( monstro );
		assertEquals( monstro, response );
	}
}
