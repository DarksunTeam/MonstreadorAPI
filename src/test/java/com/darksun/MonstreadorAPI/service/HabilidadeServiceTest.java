package com.darksun.MonstreadorAPI.service;

import com.darksun.MonstreadorAPI.entity.Habilidade;
import com.darksun.MonstreadorAPI.repository.HabilidadeRepository;
import com.darksun.MonstreadorAPI.util.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class HabilidadeServiceTest {

	@InjectMocks
	private HabilidadeService service;

	@Mock
	HabilidadeRepository repository;

	@BeforeEach
	void setUp( ) {
		MockitoAnnotations.openMocks( this );
	}

	@Test
	void buscaNaBase( ) {
		Habilidade habilidade = TestUtils.generateGenericHabilidade( );
		when( repository.findById( 1L ) ).thenReturn( Optional.ofNullable( habilidade ) );
		Habilidade response = service.buscaNaBase( habilidade.getId( ) );
		assertEquals( habilidade.getNome( ), response.getNome( ) );
		assertEquals( habilidade.getCusto( ), response.getCusto( ) );
		verify( repository, times( 1 ) ).findById( any( ) );
	}

	//	@Test
	//	TODO void validaHabilidade( ) {
	//	}

	//	@Test
	//	TODO void validaPrerequisitosHabilidades( ) {
	//	}
}
