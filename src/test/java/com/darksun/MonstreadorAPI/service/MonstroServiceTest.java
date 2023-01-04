package com.darksun.MonstreadorAPI.service;

import com.darksun.MonstreadorAPI.entity.Habilidade;
import com.darksun.MonstreadorAPI.entity.Monstro;
import com.darksun.MonstreadorAPI.exception.InvalidPrerequisitesException;
import com.darksun.MonstreadorAPI.repository.MonstroRepository;
import com.darksun.MonstreadorAPI.util.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class MonstroServiceTest {

	@InjectMocks
	MonstroService service;

	@Mock
	MonstroRepository repository;
	@Mock
	HabilidadeService habilidadeService;

	@BeforeEach
	void setUp( ) {
		MockitoAnnotations.openMocks( this );
	}

	@Test
	void buscaNaBase( ) {
		Monstro monstro = TestUtils.generateGenericMonstro( );
		when( repository.findById( 1L ) ).thenReturn( Optional.ofNullable( monstro ) );
		Monstro response = service.buscaNaBase( monstro.getId( ) );
		assertEquals( monstro.getNome( ), response.getNome( ) );
		assertEquals( monstro.getNd( ), response.getNd( ) );
		verify( repository, times( 1 ) ).findById( any( ) );
	}

	//	@Test
	//	TODO void validaMonstro( ) {
	//	}

	//	@Test
	//	TODO void validaPrerequisitosHabilidades( ) {
	//	}

	@Test
	void validaMonstroComPreRequisitoFake( ) {
		Habilidade habilidadeOriginal = TestUtils.generateHabilidadeComRequisito(
				"Trespassar Maior", "Trespassar" );

		Habilidade preRequisitoOriginal = TestUtils.generateHabilidade( 1L, "Trespassar" );

		Habilidade preRequisitoFake = TestUtils.generateHabilidade( 3L, "Ataque Poderoso" );

		Habilidade habilidadeFake = TestUtils.generateHabilidade( 2L, "Trespassar Maior" );
		habilidadeFake.addPreRequisito( preRequisitoFake );

		List< Habilidade > habilidades = new ArrayList<>( );
		habilidades.add( preRequisitoFake );
		habilidades.add( habilidadeFake );

		Monstro monstro = TestUtils.generateMonstro( "Monstro", 2, 2, habilidades,
													 new ArrayList<>( ) );

		when( habilidadeService.buscaNaBase( 1L ) ).thenReturn( preRequisitoOriginal );
		when( habilidadeService.buscaNaBase( 2L ) ).thenReturn( habilidadeOriginal );
		when( habilidadeService.buscaNaBase( 3L ) ).thenReturn( preRequisitoFake );

		try {
			service.validaPrerequisitosHabilidades( habilidades );
		} catch ( Exception ex ) {
			assertEquals( InvalidPrerequisitesException.class, ex.getClass( ) );
			assertEquals(
					"Monstro não cumpre o pré-requisito Trespassar da habilidade Trespassar Maior",
					ex.getMessage( ) );
		}
	}
}