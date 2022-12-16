package br.rn.sesed.sides.api.model.dto;

import lombok.Data;

@Data
public class UsuarioDto {

	private String nome;
	private String email;
	private String cpf;
	private String token;
}
