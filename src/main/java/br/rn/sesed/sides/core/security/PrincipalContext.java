package br.rn.sesed.sides.core.security;

import java.security.Principal;

import lombok.Data;

@Data
public class PrincipalContext implements Principal{

	private String cpf;
	private String senha;
	private String nome;

	
	public PrincipalContext(String cpf, String senha, String nome) {
		super();
		this.cpf = cpf;
		this.senha = senha;
		this.nome = nome;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
