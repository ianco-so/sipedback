package br.rn.sesed.sides.api.controller;


import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.rn.sesed.sides.api.model.dto.UsuarioDto;
import br.rn.sesed.sides.api.model.json.UsuarioJson;
import br.rn.sesed.sides.api.model.json.UsuarioLoginJson;
import br.rn.sesed.sides.api.serialization.UsuarioDtoConvert;
import br.rn.sesed.sides.api.serialization.UsuarioJsonConvert;
import br.rn.sesed.sides.core.security.Security;
import br.rn.sesed.sides.domain.exception.ErroAoDeletarUsuarioException;
import br.rn.sesed.sides.domain.exception.ErroAoSalvarUsuarioException;
import br.rn.sesed.sides.domain.exception.UsuarioNaoEncontradoException;
import br.rn.sesed.sides.domain.model.Usuario;
import br.rn.sesed.sides.domain.service.UsuarioService;

@RestController
public class UsuarioController {
	
	private static final Logger logger = LoggerFactory.getLogger(UsuarioController.class);
	
	@Autowired
	private UsuarioJsonConvert usuarioJsonConvert;

	@Autowired
	private UsuarioDtoConvert usuarioDtoConvert;

	@Autowired
	private UsuarioService usuarioService;
	

	@PostMapping("/usuario/login")
	public @ResponseBody UsuarioDto login(@RequestBody UsuarioLoginJson usuarioJson) {
		try {
			return usuarioService.autenticarUsuario(usuarioJson);
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Security
	@PostMapping("/usuario/novo")
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
	
	@Security
	@GetMapping("/usuario/recuperar/id/{id}")
	public @ResponseBody UsuarioDto recuperarUsuarioPeloId(@PathVariable("id") Long id, @Context HttpServletRequest request, @Context SecurityContext secContext) {
		try {
			Usuario usuario = usuarioService.localizarUsuarioPorId(id);
			return usuarioDtoConvert.toDto(usuario);
		} catch (Exception e) {
			throw new UsuarioNaoEncontradoException(id);
		}
	}
	
	@Security
	@GetMapping("/usuario/recuperar/nome/{nome}")
	public UsuarioDto recuperarUsuarioPeloNome(@PathVariable("nome") String nome) {
		try {
			Usuario usuario = usuarioService.localizarUsuarioPorNome(nome);
			return usuarioDtoConvert.toDto(usuario);
		} catch (Exception e) {
			throw new UsuarioNaoEncontradoException("nome",nome);
		}
	}
	
	@Security
	@GetMapping("/usuario/deletar/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void deletarUsuario(Long id) {
		try {
			//usuarioService.deletar(id);
		} catch (Exception e) {
			throw new ErroAoDeletarUsuarioException(id);
		}
	}	

}
