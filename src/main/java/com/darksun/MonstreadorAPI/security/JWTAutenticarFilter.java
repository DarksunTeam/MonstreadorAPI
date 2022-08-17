package com.darksun.MonstreadorAPI.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.darksun.MonstreadorAPI.data.DetalheUsuarioData;
import com.darksun.MonstreadorAPI.entity.Usuario;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

public class JWTAutenticarFilter extends UsernamePasswordAuthenticationFilter {

	private final AuthenticationManager authenticationManager;
	private Properties prop;

	public JWTAutenticarFilter( AuthenticationManager authenticationManager ) {
		this.authenticationManager = authenticationManager;

		try ( InputStream input = getClass( ).getClassLoader( ).getResourceAsStream( "secrets.properties" ) ) {
			this.prop = new Properties( );
			prop.load( input );
		} catch ( IOException ex ) {
			ex.printStackTrace( );
		}
	}

	@Override
	public Authentication attemptAuthentication( HttpServletRequest request,
												 HttpServletResponse response ) throws AuthenticationException {
		try {
			Usuario usuario = new ObjectMapper( ).readValue( request.getInputStream( ), Usuario.class );
			return authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken( usuario.getLogin( ), usuario.getPassword( ),
							new ArrayList<>( ) ) );
		} catch ( IOException e ) {
			throw new RuntimeException( "Falha ao autenticar usuario", e );
		}
	}

	@Override
	protected void successfulAuthentication( HttpServletRequest request, HttpServletResponse response,
											 FilterChain chain,
											 Authentication authResult ) throws IOException, ServletException {
		DetalheUsuarioData usuarioData = ( DetalheUsuarioData ) authResult.getPrincipal( );

		String token = JWT.create( ).withSubject( usuarioData.getUsername( ) ).withExpiresAt( new Date(
						System.currentTimeMillis( ) + Integer.parseInt( prop.getProperty( "jwt.expiration.token" ) ) ) )
				.sign( Algorithm.HMAC512( prop.getProperty( "jwt.password.token" ) ) );
		response.getWriter( ).write( token );
		response.getWriter( ).flush( );
	}
}
