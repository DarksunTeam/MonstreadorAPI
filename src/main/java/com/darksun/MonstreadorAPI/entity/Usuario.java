package com.darksun.MonstreadorAPI.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY )
	private Long id;
	@Column(unique = true)
	private String login;
	private String password;

	public Long getId( ) {
		return id;
	}

	public void setId( Long id ) {
		this.id = id;
	}

	public String getLogin( ) {
		return login;
	}

	public void setLogin( String login ) {
		this.login = login;
	}

	public String getPassword( ) {
		return password;
	}

	public void setPassword( String password ) {
		this.password = password;
	}
}
