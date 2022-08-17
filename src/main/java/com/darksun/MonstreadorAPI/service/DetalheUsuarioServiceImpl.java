package com.darksun.MonstreadorAPI.service;

import com.darksun.MonstreadorAPI.data.DetalheUsuarioData;
import com.darksun.MonstreadorAPI.entity.Usuario;
import com.darksun.MonstreadorAPI.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DetalheUsuarioServiceImpl implements UserDetailsService {

	private final UsuarioRepository usuarioRepository;

	public DetalheUsuarioServiceImpl( UsuarioRepository usuarioRepository ) {
		this.usuarioRepository = usuarioRepository;
	}

	@Override
	public UserDetails loadUserByUsername( String username ) throws UsernameNotFoundException {
		Optional< Usuario > usuario = usuarioRepository.findByLogin( username );

		if ( usuario.isEmpty( ) ) {
			throw new UsernameNotFoundException( "Usuário [" + username + "] não encontrado." );
		}
		return new DetalheUsuarioData( usuario );
	}
}
