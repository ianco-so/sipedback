package br.rn.sesed.sides.api.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.rn.sesed.sides.api.model.dto.UsuarioDto;
import br.rn.sesed.sides.api.model.json.UsuarioJson;
import br.rn.sesed.sides.api.model.json.UsuarioLoginJson;
import br.rn.sesed.sides.api.serialization.UsuarioDtoConvert;
import br.rn.sesed.sides.api.serialization.UsuarioJsonConvert;
import br.rn.sesed.sides.domain.exception.ErroAoDeletarUsuarioException;
import br.rn.sesed.sides.domain.exception.UsuarioNaoEncontradoException;
import br.rn.sesed.sides.domain.model.Usuario;
import br.rn.sesed.sides.domain.service.UsuarioService;
import br.rn.sesed.sides.exception.SidesException;
import lombok.Getter;

@RestController("/usuario")
public class UsuarioController {

	@Autowired
	UsuarioJsonConvert usuarioJsonConvert;

	@Autowired
	UsuarioDtoConvert usuarioDtoConvert;

	@Autowired
	UsuarioService usuarioService;

	@PostMapping("/login")
	public UsuarioDto login(UsuarioJson usuarioJson, HttpSession session) {

		Usuario usuario = usuarioJsonConvert.toDomainObject(usuarioJson);
		usuario = usuarioService.localizarUsuarioPorNome(usuario.getNome());
		
		return usuarioDtoConvert.toDto(usuario);
	}
	
	@PostMapping("/novo")
	@ResponseStatus(HttpStatus.CREATED)
	public UsuarioDto adicionar(@RequestBody UsuarioJson usuarioJson) {
		
		Usuario usuario = usuarioJsonConvert.toDomainObject(usuarioJson);

		usuario = usuarioService.salvar(usuario);

		return usuarioDtoConvert.toDto(usuario);

	}
	
	@GetMapping("/recuperar/{id}")
	public UsuarioDto recuperarUsuarioPeloId(@PathVariable("id") Long id) {
		try {
			Usuario usuario = usuarioService.localizarUsuarioPorId(id);
			return usuarioDtoConvert.toDto(usuario);
		} catch (Exception e) {
			throw new UsuarioNaoEncontradoException(id);
		}
	}
	
	@GetMapping("/recuperar/{nome}")
	public UsuarioDto recuperarUsuarioPeloNome(@PathVariable("nome") String nome) {
		try {
			Usuario usuario = usuarioService.localizarUsuarioPorNome(nome);
			usuarioDtoConvert.toDto(usuario);
		} catch (Exception e) {
			throw new UsuarioNaoEncontradoException(null,nome);
		}
		return null;
	}
	
	@GetMapping("/deletar/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void deletarUsuario(Long id) {
		try {
			usuarioService.deletar(id);
		} catch (Exception e) {
			throw new ErroAoDeletarUsuarioException(id);
		}
	}	

}
