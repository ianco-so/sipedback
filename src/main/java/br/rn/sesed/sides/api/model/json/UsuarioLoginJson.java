package br.rn.sesed.sides.api.model.json;

import lombok.Data;

@Data
public class UsuarioLoginJson{
	
	private String cnpj;
	private String code;
	private String cpf;
	private String nome;
	private String senha;
	private String setor;
	
}
