package br.rn.sesed.sides.infrastructure.authentication;

import java.io.IOException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.rn.sesed.sides.api.model.json.UsuarioLoginJson;
import br.rn.sesed.sides.exception.SidesException;
import br.rn.sesed.sides.infrastructure.authentication.JWTModel.DetalhesUsuarioData;

@Component
public class _JWTAutenticationfilter extends UsernamePasswordAuthenticationFilter {
	
	private final Integer EXPIRATION_TIME = 180;
	public static final String TOKEN_SENHA = "93ae2808505844f694d9fc409526ec05";
	
	@Autowired
	private final AuthenticationManager authenticationManager;

    public _JWTAutenticationfilter(AuthenticationManager authenticationManager) {
        
    }

	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws SidesException  {
		try {
			UsuarioLoginJson usuario = new ObjectMapper().readValue(request.getInputStream(), UsuarioLoginJson.class);
			return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
							usuario.getCpf(), 
							usuario.getSenha(),
							new ArrayList<>()));
		} catch (IOException e) {
			throw new SidesException("Falha ao autenticar usuario");
		}
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		DetalhesUsuarioData detalhesUsuarioData = (DetalhesUsuarioData) authResult.getPrincipal();
		Calendar date = Calendar.getInstance();
		date.add(Calendar.MINUTE, EXPIRATION_TIME);
		String token = JWT.create()
				.withSubject(detalhesUsuarioData.getUsername())
				.withExpiresAt(date.getTime())
				.sign(Algorithm.HMAC512(TOKEN_SENHA));
		
		response.getWriter().write(token);
		response.getWriter().flush();
				
	}

	
	

}

