package br.rn.sesed.sides.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.rn.sesed.sides.api.model.dto.UsuarioDto;
import br.rn.sesed.sides.api.model.json.UsuarioJson;
import br.rn.sesed.sides.api.model.json.UsuarioLoginJson;
import br.rn.sesed.sides.api.serialization.UsuarioDtoConvert;
import br.rn.sesed.sides.api.serialization.UsuarioJsonConvert;
import br.rn.sesed.sides.domain.model.Usuario;
import br.rn.sesed.sides.domain.service.CadastroUsuarioService;
import lombok.Getter;

@RestController
public class UsuarioController {

	@Autowired
	UsuarioJsonConvert usuarioJsonConvert;

	@Autowired
	UsuarioDtoConvert usuarioDtoConvert;

	@Autowired
	CadastroUsuarioService cadastroUsuarioService;

	@PostMapping("/login")
	public UsuarioDto getLogin(UsuarioJson usuarioJson) {

		Usuario usuario = usuarioJsonConvert.toDomainObject(usuarioJson);
		usuario = cadastroUsuarioService.localizarUsuarioPorNome(usuario.getNome());
		
		return usuarioDtoConvert.toDto(usuario);
	}

	@PostMapping()
	@ResponseStatus(HttpStatus.CREATED)
	public UsuarioDto adicionar(@RequestBody UsuarioJson usuarioJson) {
		
		Usuario usuario = usuarioJsonConvert.toDomainObject(usuarioJson);

		usuario = cadastroUsuarioService.salvar(usuario);

		return usuarioDtoConvert.toDto(usuario);

	}

}
