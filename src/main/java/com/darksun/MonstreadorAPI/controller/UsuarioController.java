package com.darksun.MonstreadorAPI.controller;

import com.darksun.MonstreadorAPI.entity.Usuario;
import com.darksun.MonstreadorAPI.repository.UsuarioRepository;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping( "/api" )
public class UsuarioController {

	private final UsuarioRepository usuarioRepository;
	private final PasswordEncoder encoder;

	public UsuarioController( UsuarioRepository usuarioRepository, PasswordEncoder encoder ) {
		this.usuarioRepository = usuarioRepository;
		this.encoder = encoder;
	}

	//	@GetMapping( "/usuarios" )
	//	@ApiOperation( value = "Retorna todos os usuários cadastrados na base" )
	//	public List< Usuario > listaUsuarios( ) {
	//		return usuarioRepository.findAll( );
	//	}

	//	@PostMapping( "/usuario" )
	//	@ApiOperation( value = "Cadastra um novo usuário na base" )
	//	public ResponseEntity< Usuario > salvar( @RequestBody Usuario usuario ) {
	//		usuario.setPassword( encoder.encode( usuario.getPassword( ) ) );
	//		return ResponseEntity.ok( usuarioRepository.save( usuario ) );
	//	}

	@GetMapping( "/authenticate" )
	@ApiOperation( value = "Valida credenciais do usuário" )
	public ResponseEntity< Boolean > validarSenha( @RequestParam String login, @RequestParam String password ) {
		Optional< Usuario > optUsuario = usuarioRepository.findByLogin( login );
		if ( optUsuario.isEmpty( ) ) {
			return ResponseEntity.status( HttpStatus.UNAUTHORIZED ).body( false );
		}

		Usuario usuario = optUsuario.get( );
		Boolean valid = encoder.matches( password, usuario.getPassword( ) );

		HttpStatus status = ( valid ) ? HttpStatus.OK : HttpStatus.UNAUTHORIZED;

		return ResponseEntity.status( status ).body( valid );
	}
}
