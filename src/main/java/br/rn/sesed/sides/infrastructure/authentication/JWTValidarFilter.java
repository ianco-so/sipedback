package br.rn.sesed.sides.infrastructure.authentication;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

public class JWTValidarFilter extends BasicAuthenticationFilter {
	
	public static final String HEADER_PARAM = "Authorization";
	public static final String PARAM_PREFIX = "Bearer ";

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)throws IOException, ServletException {
		String attr = request.getHeader(HEADER_PARAM);
		if(attr == null || !attr.startsWith(PARAM_PREFIX)) {
			chain.doFilter(request, response);
			return;
		}
	}
	
}
