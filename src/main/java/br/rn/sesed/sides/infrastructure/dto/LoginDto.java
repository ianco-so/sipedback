package br.rn.sesed.sides.infrastructure.dto;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class LoginDto {
	
	private String token;
	private String revalida;
	private String email;
	private String nome;
	private String cpf;
	
}
