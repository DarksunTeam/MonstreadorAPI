package com.darksun.MonstreadorAPI.service;

import com.darksun.MonstreadorAPI.entity.Habilidade;
import com.darksun.MonstreadorAPI.entity.Monstro;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MonstroService {

	public Boolean validaMonstro( Monstro monstro ) {
		Boolean result = true;
		if ( !validaPrerequisitosHabilidades( monstro.getHabilidades( ) ) ) {
			result = false;
		}
		return result;
	}

	public Boolean validaPrerequisitosHabilidades( List< Habilidade > habilidades ) {
		for ( Habilidade habilidade : habilidades ) {
			for ( Habilidade preRequisito : habilidade.getPreRequisitos( ) ) {
				if ( !habilidades.contains( preRequisito ) ) {
					return false;
				}
			}
		}
		return true;
	}
}
