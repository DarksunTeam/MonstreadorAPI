package com.darksun.MonstreadorAPI.entity;

import java.io.Serializable;

public enum TipoDano implements Serializable {
	CORTANTE( 1 ),
	PERFURANTE( 2 ),
	CONCUSSIVO( 3 ),
	MAGICO( 4 );

	private Integer id;

	TipoDano( int id ) {
		this.id = id;
	}
}
