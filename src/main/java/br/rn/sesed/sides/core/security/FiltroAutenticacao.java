package br.rn.sesed.sides.core.security;

import java.io.IOException;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import org.springframework.beans.factory.annotation.Autowired;

import io.jsonwebtoken.Claims;


//Defini que a @Security ira utilizar essa classe
@Security
// Indica que essa classe vai prover a funcionalidade pra @seguro não o contario
@Provider
// E prioridade de execucao, pois podemos ter outras classe filtro
// que devem ser executas em uma ordem expecifica
@Priority(Priorities.AUTHENTICATION)
public class FiltroAutenticacao implements ContainerRequestFilter {
	
	@Autowired
	private GenerateToken generateToken;
	
//	@Inject
//	private SistemaImpl sistemaImpl;
	
	
	// Aqui fazemos o override do método filter que tem como parâmetro
	// o ContainerRequestContext que é o objeto que podemos manipular a request
	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		// Verifica se o header AUTHORIZATION existe ou não se existe extrai o token
		// se não aborta a requisição retornando uma NotAuthorizedException
		
		String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
		
		if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
			throw new NotAuthorizedException("Authorization header precisa ser provido");
		}

		// extrai o token do header
		String token = authorizationHeader.substring("Bearer".length()).trim();
		
		try {
			
			// método que verifica se o token é valido ou não
			Claims claims = generateToken.validaToken(token);

			// Caso não for valido vai retornar um objeto nulo e executar um exception
			if (claims == null) {
				throw new Exception("Token inválido");
			}
			// Método que modifica o SecurityContext pra disponibilizar o login do usuario
			modificarRequestContext(requestContext, claims.getIssuer(),  claims.getOrDefault("name", "").toString(), claims.getOrDefault("email", "").toString());

		} catch (Exception e) {

			//e.printStackTrace();

			// Caso o token for invalido a requisição é abortada e retorna uma resposta com
			// status 401 UNAUTHORIZED
			requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());

		}

	}

	// Método que modifica o SecurityContext

	private void modificarRequestContext(ContainerRequestContext requestContext, String cpf, String senha, String nome) {
		
		//final SecurityContext currentSecurityContext = requestContext.getSecurityContext();
		
		SecuryContext rotafxSecuryContext = new SecuryContext(cpf, senha, nome);
		
		requestContext.setSecurityContext(rotafxSecuryContext);

	}
	
	

}
