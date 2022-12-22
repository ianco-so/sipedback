package br.rn.sesed.sides.core.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import br.rn.sesed.sides.domain.service.DetalhesUsuarioServiceImpl;
import br.rn.sesed.sides.infrastructure.authentication.JWTAutenticationfilter;
import br.rn.sesed.sides.infrastructure.authentication.JWTValidarFilter;

@SuppressWarnings("deprecation")
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private DetalhesUsuarioServiceImpl usuarioservice;
	
	@Autowired
	private PasswordEncoder encoder;	

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(usuarioservice).passwordEncoder(encoder);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		/*
		 * 
		* HABILITAR QUANDO FOR PARA PRODUÇÃO
		* 
		*/
		http.csrf().disable().authorizeRequests()
					.antMatchers(HttpMethod.POST,"/login").permitAll()
					.anyRequest().authenticated()
					.and()
					.addFilter(new JWTAutenticationfilter())
					.addFilter(new JWTValidarFilter(authenticationManager()))
					.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}
	
	/*
	//Liberação para requisições externas
	@Bean
	public CorsConfigurationSource corsConfiguration() {
		
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration corsConfiguration = new CorsConfiguration().applyPermitDefaultValues();
		source.registerCorsConfiguration("/**", corsConfiguration);
		return source;
	}
	*/
	
}