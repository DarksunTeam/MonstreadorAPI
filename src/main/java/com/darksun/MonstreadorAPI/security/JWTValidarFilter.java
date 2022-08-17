package com.darksun.MonstreadorAPI.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

public class JWTValidarFilter extends BasicAuthenticationFilter {

	public static final String HEADER_ATRIBUTO = "Authorization";
	public static final String ATRIBUTO_PREFIXO = "Bearer";

	private final Properties prop = new Properties( );

	public JWTValidarFilter( AuthenticationManager authenticationManager ) {
		super( authenticationManager );
	}

	@Override
	protected void doFilterInternal( HttpServletRequest request, HttpServletResponse response,
									 FilterChain chain ) throws IOException, ServletException {
		String atributo = request.getHeader( HEADER_ATRIBUTO );

		if ( atributo == null || !atributo.startsWith( ATRIBUTO_PREFIXO ) ) {
			chain.doFilter( request, response );
			return;
		}

		String token = atributo.replace( ATRIBUTO_PREFIXO, "" );
		UsernamePasswordAuthenticationToken authenticationToken = getAuthenticationToken( token );

		SecurityContextHolder.getContext( ).setAuthentication( authenticationToken );
		chain.doFilter( request, response );
	}

	private UsernamePasswordAuthenticationToken getAuthenticationToken( String token ) {
		String usuario = JWT.require( Algorithm.HMAC512( prop.getProperty( "jwt.password.token" ) ) )
				.build( )
				.verify( token )
				.getSubject( );

		if ( usuario == null ) {
			return null;
		}

		return new UsernamePasswordAuthenticationToken( usuario, null, new ArrayList<>( ) );
	}

}
