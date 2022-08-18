package com.darksun.MonstreadorAPI.config;

import springfox.documentation.service.*;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import static springfox.documentation.builders.PathSelectors.regex;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

@Configuration
public class SwaggerConfig {

	private Properties prop = new Properties( );

	@Bean
	public Docket productApi( ) {
		return new Docket( DocumentationType.SWAGGER_2 ).securityContexts( Arrays.asList( securityContext( ) ) )
				.securitySchemes( Arrays.asList( apiKey( ) ) )
				.select( )
				.apis( RequestHandlerSelectors.basePackage( "com.darksun.MonstreadorAPI" ) )
				.paths( regex( "/api.*" ) )
				.build( )
				.apiInfo( metaInfo( ) );
	}

	private ApiInfo metaInfo( ) {

		ApiInfo apiInfo = new ApiInfo( "Monstreador API REST", "API REST de cadastro de Monstros de RPG.", "1.0.0",
				"Terms of Service",
				new Contact( "Marcos Gonçalves", "https://github.com/coppolaop", "mag.junior@hotmail.com" ),
				"Apache License Version 2.0", "https://www.apache.org/licesen.html",
				new ArrayList< VendorExtension >( ) );

		return apiInfo;
	}

	private ApiKey apiKey( ) {
		try ( InputStream input = getClass( ).getClassLoader( ).getResourceAsStream( "application.properties" ) ) {
			this.prop = new Properties( );
			prop.load( input );
		} catch ( IOException ex ) {
			ex.printStackTrace( );
		}

		return new ApiKey( "JWT", prop.getProperty( "jwt.header" ), "header" );
	}

	private SecurityContext securityContext( ) {
		return SecurityContext.builder( ).securityReferences( defaultAuth( ) ).build( );
	}

	List< SecurityReference > defaultAuth( ) {
		AuthorizationScope authorizationScope = new AuthorizationScope( "global", "accessEverything" );
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		return Arrays.asList( new SecurityReference( "JWT", authorizationScopes ) );
	}
}