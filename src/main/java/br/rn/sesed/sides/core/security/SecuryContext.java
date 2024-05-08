package br.rn.sesed.sides.core.security;

import java.security.Principal;

import javax.ws.rs.core.SecurityContext;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SecuryContext implements SecurityContext{

	private String cpf;
	private String senha;
	private String nome;
	
	public SecuryContext() {
		super();
	}

	public SecuryContext(String cpf, String senha, String nome) {
		super();
		this.cpf = cpf;
		this.senha = senha;
		this.nome = nome;
	}

	@Override
	public String getAuthenticationScheme() {
		return "Bearer";
	}

	@Override
	public Principal getUserPrincipal() {
		PrincipalContext rotafxPrincipal = new PrincipalContext(cpf, senha, nome);
		return rotafxPrincipal;
	}

	@Override
	public boolean isSecure() {
		return this.isSecure();
	}

	@Override
	public boolean isUserInRole(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

}
