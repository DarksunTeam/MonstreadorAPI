package com.darksun.MonstreadorAPI.security;

import com.darksun.MonstreadorAPI.service.DetalheUsuarioServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableWebSecurity
public class JWTConfiguracao extends WebSecurityConfigurerAdapter {

	private final DetalheUsuarioServiceImpl usuarioService;
	private final PasswordEncoder passwordEncoder;

	public JWTConfiguracao( DetalheUsuarioServiceImpl usuarioService,
							PasswordEncoder passwordEncoder ) {
		this.usuarioService = usuarioService;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	protected void configure( AuthenticationManagerBuilder auth ) throws Exception {
		auth.userDetailsService( usuarioService ).passwordEncoder( passwordEncoder );
	}

	private static final String[] AUTH_WHITELIST = { "/v3/api-docs/**", "/swagger-ui/**",
			"/swagger-ui.html", "/swagger-resources/**", "/v2/api-docs", "/webjars/**" };

	@Override
	protected void configure( HttpSecurity http ) throws Exception {
		http.csrf( )
				.disable( )
				.authorizeRequests( )
				.antMatchers( AUTH_WHITELIST )
				.permitAll( )
				.antMatchers( HttpMethod.POST, "/login" )
				.permitAll( )
				.anyRequest( )
				.authenticated( )
				.and( )
				.httpBasic( )
				.authenticationEntryPoint( swaggerAuthenticationEntryPoint( ) )
				.and( )
				.addFilter( new JWTAutenticarFilter( authenticationManager( ) ) )
				.addFilter( new JWTValidarFilter( authenticationManager( ) ) )
				.sessionManagement( )
				.sessionCreationPolicy( SessionCreationPolicy.STATELESS );
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource( ) {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource( );

		CorsConfiguration corsConfiguration = new CorsConfiguration( ).applyPermitDefaultValues( );
		source.registerCorsConfiguration( "/**", corsConfiguration );
		return source;
	}

	@Bean
	public BasicAuthenticationEntryPoint swaggerAuthenticationEntryPoint( ) {
		BasicAuthenticationEntryPoint entryPoint = new BasicAuthenticationEntryPoint( );
		entryPoint.setRealmName( "Swagger Realm" );
		return entryPoint;
	}
}
