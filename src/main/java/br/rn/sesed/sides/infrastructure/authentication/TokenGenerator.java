package br.rn.sesed.sides.infrastructure.authentication;

import java.util.Calendar;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;


@Component
public class TokenGenerator {
	
	private static final Integer VALIDADE_MINUTES = 60;
	private static final String PRIVATE_KEY = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQDK7c0HtOvefMRM";
	public static final String HEADER_STRING = "Authorization";
	public static final String TOKEN_PREFIX = "Bearer ";
	
	public TokenGenerator() {}
	
	public String gerarToken(String cpf) {
		Calendar tempoExpiracao = Calendar.getInstance();
    	tempoExpiracao.add(Calendar.MINUTE, VALIDADE_MINUTES);    	
        String token = JWT.create()
        		.withSubject(cpf)
        		.withExpiresAt(tempoExpiracao.getTime())
        		.sign(Algorithm.HMAC512(PRIVATE_KEY.getBytes()));
        return token;
	}
	
	public String decodificarToken(String token) {
		String retorno = JWT.require(Algorithm.HMAC512(PRIVATE_KEY.getBytes()))
                .build()
                .verify(token.replace(TOKEN_PREFIX, ""))
                .getSubject();
		return retorno;
	}
}
