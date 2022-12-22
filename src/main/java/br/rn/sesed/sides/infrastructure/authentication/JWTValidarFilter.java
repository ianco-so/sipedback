package br.rn.sesed.sides.infrastructure.authentication;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

public class JWTValidarFilter extends BasicAuthenticationFilter {

	public static final String HEADER_PARAM = "Authorization";
	public static final String PARAM_PREFIX = "Bearer ";
	
	public JWTValidarFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)throws IOException, ServletException {
		String attr = request.getHeader(HEADER_PARAM);
		if(attr == null || !attr.startsWith(PARAM_PREFIX)) {
			chain.doFilter(request, response);
			return;
		}
		String token = attr.replace(PARAM_PREFIX, "");
		UsernamePasswordAuthenticationToken authenticationToken = getAuthenticationToken(token);
		SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		chain.doFilter(request, response);
	}
	
	private UsernamePasswordAuthenticationToken getAuthenticationToken(String token) {
		String usuario = JWT.require(Algorithm.HMAC512(JWTAutenticationfilter.TOKEN_SENHA))
				.build()
				.verify(token)
				.getSubject();
		if (usuario == null) {
			return null;
		}
		return new UsernamePasswordAuthenticationToken(usuario, null, new ArrayList<>());
	}
	
}
