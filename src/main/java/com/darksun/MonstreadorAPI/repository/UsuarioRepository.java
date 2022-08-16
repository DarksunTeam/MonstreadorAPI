package com.darksun.MonstreadorAPI.repository;

import com.darksun.MonstreadorAPI.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository< Usuario, Long> {
	public Optional<Usuario> findByLogin(String login);
}
