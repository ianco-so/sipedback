package br.rn.sesed.sides.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.rn.sesed.sides.api.model.dto.UsuarioDto;
import br.rn.sesed.sides.api.model.json.UsuarioJson;
import br.rn.sesed.sides.api.serialization.UsuarioDtoConvert;
import br.rn.sesed.sides.api.serialization.UsuarioJsonConvert;
import br.rn.sesed.sides.core.security.annotation.Security;
import br.rn.sesed.sides.domain.desaparecidos.model.Usuario;
import br.rn.sesed.sides.domain.desaparecidos.service.UsuarioService;
import br.rn.sesed.sides.domain.exception.ErroAoSalvarUsuarioException;

@RestController
@RequestMapping("/pessoa")
public class PessoaController {

	
	@Autowired
	private UsuarioJsonConvert usuarioJsonConvert;
	
	@Autowired
	private UsuarioDtoConvert usuarioDtoConvert;

	@Autowired
	private UsuarioService usuarioService;

	@Security
	@PostMapping("/novo")
	@ResponseStatus(HttpStatus.CREATED)
	public @ResponseBody UsuarioDto adicionar(@RequestBody UsuarioJson usuarioJson) {
		try {
			Usuario usuario = usuarioJsonConvert.toDomainObject(usuarioJson);
			usuarioService.salvar(usuario);
			return usuarioDtoConvert.toDto(usuario);
		} catch (Exception e) {
			throw new ErroAoSalvarUsuarioException(e.getMessage());
		}
	}
	

	

}
