package br.rn.sesed.sides.api.model.dto;

import lombok.Data;

@Data
public class UsuarioDto {

	private String nome;
	private String cpf;
	private String email;
	private String telefone;
	private String token;
	private String instituicao;
	private Boolean boAtivo;
	private Boolean boValidado;
}
