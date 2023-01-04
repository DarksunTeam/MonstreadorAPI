package com.darksun.MonstreadorAPI.util;

import com.darksun.MonstreadorAPI.entity.Ataque;
import com.darksun.MonstreadorAPI.entity.Habilidade;
import com.darksun.MonstreadorAPI.entity.Monstro;

import java.util.ArrayList;
import java.util.List;

public class TestUtils {

	public static List< Monstro > generateListMonstro( ) {
		List< Monstro > monstros = new ArrayList<>( );

		monstros.add( generateMonstro( "Base", -4, 0 ) );
		monstros.add( generateMonstro( "ND1", 1, 0 ) );
		monstros.add( generateMonstro( "ND1I1", 1, 1 ) );
		monstros.add( generateMonstro( "ND2", 2, 0 ) );
		monstros.add( generateMonstro( "ND2I2", 2, 2 ) );
		monstros.add( generateMonstro( "ND3", 3, 0 ) );
		monstros.add( generateMonstro( "ND4", 4, 0 ) );
		monstros.add( generateMonstro( "ND5", 5, 0 ) );

		return monstros;
	}

	public static Monstro generateMonstro( String nome, Integer nivel, Integer incremental ) {
		return generateMonstro( nome, nivel, incremental, new ArrayList<>( ), new ArrayList<>( ) );
	}

	public static Monstro generateGenericMonstro( ) {
		return generateMonstro( "Monstro", 1, 0 );
	}

	public static Monstro generateMonstroQuebraRequisito( String nome, Integer nivel,
														  Integer incremental ) {
		List< Habilidade > habilidades = new ArrayList<>( );
		habilidades.add( generateHabilidadeComRequisito( "Trespassar Maior", "Trespassar" ) );
		return generateMonstro( nome, nivel, incremental, habilidades, new ArrayList<>( ) );
	}

	public static Monstro generateMonstro( String nome, Integer nivel, Integer incremental,
										   List< Habilidade > habilidades,
										   List< Ataque > ataques ) {
		return Monstro.builder( )
					  .id( 1L )
					  .nome( nome )
					  .forca( 10 + incremental )
					  .destreza( 10 + incremental )
					  .constituicao( 10 + incremental )
					  .inteligencia( 10 + incremental )
					  .sabedoria( 10 + incremental )
					  .carisma( 10 + incremental )
					  .pontosDeVida( ( 5 + ( incremental / 2 ) ) * nivel )
					  .pontosDeMana( ( 1 + ( incremental / 2 ) ) * nivel )
					  .iniciativa( incremental / 2 )
					  .percepcao( ( incremental / 2 ) )
					  .defesa( 10 )
					  .fortitude( ( incremental / 2 ) )
					  .reflexo( ( incremental / 2 ) )
					  .vontade( ( incremental / 2 ) )
					  .velocidadeAndar( 9. )
					  .velocidadeEscalar( 0. )
					  .velocidadeNadar( 0. )
					  .velocidadeVoar( 0. )
					  .nd( nivel )
					  .habilidades( habilidades )
					  .ataques( ataques )
					  .build( );
	}

	public static List< Habilidade > generateListHabilidade( ) {
		List< Habilidade > habilidades = new ArrayList<>( );

		habilidades.add( generateHabilidade( 1L, "Trespassar" ) );
		habilidades.add( generateHabilidadeComRequisito( "Trespassar Maior", "Trespassar" ) );
		habilidades.add( generateHabilidade( 3L, "Ataque Poderoso" ) );
		habilidades.add( generateHabilidade( 4L, "Trespassar Maior" ) );
		habilidades.add( generateHabilidade( 5L, "Vitalidade" ) );
		habilidades.add( generateHabilidade( 6L, "Vontade de ferro" ) );
		habilidades.add( generateHabilidade( 7L, "Magia acelerada" ) );
		habilidades.add( generateHabilidade( 8L, "Saltar e Golpear" ) );
		habilidades.add( generateHabilidade( 9L, "Voar" ) );
		habilidades.add( generateHabilidade( 10L, "Nadar" ) );

		return habilidades;
	}

	public static Habilidade generateHabilidade( Long id, String nome ) {
		return Habilidade.builder( )
						 .id( id )
						 .nome( nome )
						 .descricao( "" )
						 .custo( 0 )
						 .preRequisitos( new ArrayList<>( ) )
						 .build( );
	}

	public static Habilidade generateGenericHabilidade( ) {
		return generateHabilidade( 1L, "Habilidade" );
	}

	public static Habilidade generateHabilidadeComRequisito( String nome, String nomeReq ) {
		Habilidade habilidade = generateHabilidade( 2L, nome );
		habilidade.addPreRequisito( generateHabilidade( 1L, nomeReq ) );
		return habilidade;
	}
}
