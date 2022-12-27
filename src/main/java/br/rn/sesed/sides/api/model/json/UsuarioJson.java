package br.rn.sesed.sides.api.model.json;

import lombok.Data;

@Data
public class UsuarioJson {

	private String nome;
	private String email;
	private String senha;
	private String cpf;
	private String telefone;
	
}
