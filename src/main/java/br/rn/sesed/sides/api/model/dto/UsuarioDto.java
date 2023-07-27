package br.rn.sesed.sides.api.model.dto;

import lombok.Data;

@Data
public class UsuarioDto {
	private Long id;
	private String nome;
	private String cpf;
	private String telefone;
	private String email;
	private String token;
	private String instituicao;
	private Boolean boValidado;
	private Boolean boAtivo;
}
