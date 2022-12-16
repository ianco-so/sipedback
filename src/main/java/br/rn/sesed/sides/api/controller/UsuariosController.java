package br.rn.sesed.sides.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.rn.sesed.sides.domain.repository.UsuarioRepository;
import br.rn.sesed.sides.infrastructure.dto.UsuarioDto;

@RestController("/usuarios")
public class UsuariosController {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@GetMapping("/novo")
	@ResponseStatus(code = HttpStatus.CREATED)
	public UsuarioDto novoUsuario() {
		try {
			
			usuarioRepository.sa
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;		
	}
	

}
