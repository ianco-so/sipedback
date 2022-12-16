package br.rn.sesed.sides.infrastructure.dto;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class UsuarioDto {

private Long id;
	
	private String nome;
	private String cpf;
	private String email;
	private String telefone;
	private String senha;
	
}
