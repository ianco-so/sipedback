package br.rn.sesed.sides.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.rn.sesed.sides.domain.model.Usuario;
import br.rn.sesed.sides.domain.service.CadastroUsuarioService;

@RestController
@RequestMapping("/")
public class SidesController {
	
	@Autowired
	BuildProperties buildProperties;
	
	@Autowired 
	CadastroUsuarioService cadastroUsuarioService;
	
	
	@GetMapping("/version")
	public BuildProperties version() {
		
		Usuario usuario = cadastroUsuarioService.localizarUsuario(1L);
		
		return buildProperties;
	}	
	
}
