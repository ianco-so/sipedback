package br.rn.sesed.sides.core.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig implements WebSecurityCustomizer{

	@Override
	public void customize(WebSecurity web) {	
		
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
////
////		http
////		  .csrf()
////		  .disable()
////	      .authorizeRequests()
////	        .anyRequest().fullyAuthenticated()
////	        .and()
////	      .formLogin()
////	      .defaultSuccessUrl("/swagger-ui");
////		
////		
////		
////		
		http
		  .csrf()
		  .disable()
	      .authorizeRequests()
	        .anyRequest()
	        .permitAll();
////	        .authenticated();
////		
////
	    return http.build();
	}
	
//	@Autowired
//	public void configure(AuthenticationManagerBuilder auth) throws Exception {
//		
//		auth
//	      .ldapAuthentication()	      	
//	        .userDnPatterns("uid={0},ou=SESED,ou=CTINF")
//	        .groupSearchBase("ou=SESED")
//	        .contextSource()
//	          .url("ldap://10.9.100.101/dc=sesed,dc=interno")
//	          .and()
//	        .passwordCompare()
//	          .passwordEncoder(new BCryptPasswordEncoder())
//	          .passwordAttribute("pwd");
//	}
//	

//	private AuthenticationProvider authLdapProvider() {
//		
//	
//		
//		customLdapAuthenticationProvider.setUseAuthenticationRequestCredentials(true);
//		customLdapAuthenticationProvider.setUserDetailsContextMapper(userDetailsContextMapper());
//		
//		return customLdapAuthenticationProvider;
//		return null;
//	}
	
//	@Bean
//	public WebSecurityCustomizer webSecurityCustomizer() {
//	    return (web) -> web.debug(false)
//	      .ignoring()
//	      .antMatchers("/css/**", "/js/**", "/img/**", "/lib/**", "/favicon.ico");
//	}
		
//    @Bean
//    ApacheDSContainer ldapContainer() throws Exception {
//        return new ApacheDSContainer("dc=baeldung,dc=com", "classpath:users.ldif");
//    }

//    @Bean
//    LdapAuthoritiesPopulator authorities(BaseLdapPathContextSource contextSource) {
//        String groupSearchBase = "ou=SESED";
//        DefaultLdapAuthoritiesPopulator authorities = new DefaultLdapAuthoritiesPopulator(contextSource, groupSearchBase);
//        authorities.setGroupSearchFilter("(member={0})");
//        return authorities;
//    }
//
//	@Bean
//	AuthenticationManager authenticationManager(BaseLdapPathContextSource contextSource, LdapAuthoritiesPopulator authorities) {
//		LdapBindAuthenticationManagerFactory factory = new LdapBindAuthenticationManagerFactory(contextSource);
//		factory.setUserDnPatterns("uid={0},ou=SESED,ou=CTINF");
//		factory.setLdapAuthoritiesPopulator(authorities);
//		return factory.createAuthenticationManager();
//	}

    
}
