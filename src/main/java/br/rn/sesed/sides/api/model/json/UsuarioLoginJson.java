package br.rn.sesed.sides.api.model.json;

import lombok.Data;

@Data
public class UsuarioLoginJson{
	
	private String cpf;
	private String cnpj;
	private String senha;

	// @JsonIgnore
	// private String code;
	
	// @JsonIgnore
	// private String setor;
	
}
