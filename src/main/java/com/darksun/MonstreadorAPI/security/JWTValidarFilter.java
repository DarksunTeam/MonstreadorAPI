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
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

public class JWTValidarFilter extends BasicAuthenticationFilter {

	private final Properties prop = new Properties( );

	public JWTValidarFilter( AuthenticationManager authenticationManager ) {
		super( authenticationManager );

		try ( InputStream input = getClass( ).getClassLoader( ).getResourceAsStream( "application.properties" ) ) {
			prop.load( input );
		} catch ( IOException ex ) {
			ex.printStackTrace( );
		}

		try ( InputStream input = getClass( ).getClassLoader( ).getResourceAsStream( "secrets.properties" ) ) {
			prop.load( input );
		} catch ( IOException ex ) {
			ex.printStackTrace( );
		}
	}

	@Override
	protected void doFilterInternal( HttpServletRequest request, HttpServletResponse response,
									 FilterChain chain ) throws IOException, ServletException {
		String atributo = request.getHeader( prop.getProperty( "jwt.header" ) );

		if ( atributo == null || !atributo.startsWith( prop.getProperty( "jwt.prefix" ) ) ) {
			chain.doFilter( request, response );
			return;
		}

		String token = atributo.replace( prop.getProperty( "jwt.prefix" ), "" ).replaceAll( "\\s", "" );
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
