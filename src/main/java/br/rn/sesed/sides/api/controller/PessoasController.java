package br.rn.sesed.sides.api.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import br.rn.sesed.sides.infrastructure.dto.LoginDto;
import br.rn.sesed.sides.infrastructure.model.Usuarios;

@RestController
public class PessoasController {
	
	@PostMapping("/login")
	public LoginDto getLogin(Usuarios user) {
		
		
		return loginDto;
	} 

}
