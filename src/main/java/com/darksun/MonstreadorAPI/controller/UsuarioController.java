package com.darksun.MonstreadorAPI.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.darksun.MonstreadorAPI.entity.Usuario;
import com.darksun.MonstreadorAPI.repository.UsuarioRepository;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

@RestController
@RequestMapping( "/api" )
public class UsuarioController {

	private final UsuarioRepository usuarioRepository;
	private final PasswordEncoder   encoder;

	@Value( "${jwt.password.token}" )
	private String passwordToken;

	private final Properties prop = new Properties( );

	public UsuarioController( UsuarioRepository usuarioRepository, PasswordEncoder encoder ) {
		this.usuarioRepository = usuarioRepository;
		this.encoder           = encoder;

		try ( InputStream input = getClass( ).getClassLoader( )
											 .getResourceAsStream( "application.properties" ) ) {
			prop.load( input );
		} catch ( IOException ex ) {
			ex.printStackTrace( );
		}
	}

	@GetMapping( "/usuarios" )
	@ApiOperation( value = "Retorna todos os usuários cadastrados na base" )
	public List< Usuario > listaUsuarios( ) {
		return usuarioRepository.findAll( );
	}

	@PostMapping( "/usuario" )
	@ApiOperation( value = "Cadastra um novo usuário na base" )
	public ResponseEntity< String > salvar( @RequestBody Usuario usuario,
											@RequestHeader( "token" ) String token ) {
		if ( passwordToken.equals( token ) ) {
			usuario.setPassword( encoder.encode( usuario.getPassword( ) ) );
			return ResponseEntity.status( HttpStatus.CREATED )
								 .body( usuarioRepository.save( usuario ).toString( ) );
		} else {
			return ResponseEntity.status( HttpStatus.UNAUTHORIZED ).body( "Token inválido" );
		}
	}

	@PostMapping( "/authenticate" )
	@ApiOperation( value = "Valida credenciais do usuário e gera token JWT" )
	public ResponseEntity< String > validarSenha( @RequestBody Usuario usuario ) {
		Optional< Usuario > optUsuario = usuarioRepository.findByLogin( usuario.getLogin( ) );
		if ( optUsuario.isEmpty( ) ) {
			return ResponseEntity.status( HttpStatus.UNAUTHORIZED ).body( "Credenciais inválidas" );
		}

		Usuario dbUsuario = optUsuario.get( );
		Boolean valid     = encoder.matches( usuario.getPassword( ), dbUsuario.getPassword( ) );

		HttpStatus status = HttpStatus.UNAUTHORIZED;
		String     token  = "";

		if ( valid ) {
			status = HttpStatus.OK;
			token  = JWT.create( )
						.withSubject( usuario.getLogin( ) )
						.withExpiresAt( new Date( System.currentTimeMillis( ) + Integer.parseInt(
								prop.getProperty( "jwt.expiration.token" ) ) ) )
						.sign( Algorithm.HMAC512( prop.getProperty( "jwt.password.token" ) ) );
		}

		return ResponseEntity.status( status ).body( token );
	}
}
