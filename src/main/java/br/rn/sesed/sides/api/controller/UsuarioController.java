package br.rn.sesed.sides.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.rn.sesed.sides.api.model.dto.UsuarioDto;
import br.rn.sesed.sides.api.model.json.UsuarioJson;
import br.rn.sesed.sides.api.model.json.UsuarioLoginJson;
import br.rn.sesed.sides.api.serialization.UsuarioDtoConvert;
import br.rn.sesed.sides.api.serialization.UsuarioJsonConvert;
import br.rn.sesed.sides.core.security.Encrypt;
import br.rn.sesed.sides.core.security.annotation.Security;
import br.rn.sesed.sides.domain.desaparecidos.model.Pessoa;
import br.rn.sesed.sides.domain.desaparecidos.model.Usuario;
import br.rn.sesed.sides.domain.desaparecidos.service.UsuarioService;
import br.rn.sesed.sides.domain.exception.EntidadeNaoEncontradaException;
import br.rn.sesed.sides.domain.exception.ErroAoSalvarUsuarioException;
import br.rn.sesed.sides.domain.exception.UsuarioNaoEncontradoException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {



	@Autowired
	private UsuarioJsonConvert usuarioJsonConvert;
	
	@Autowired
	private UsuarioDtoConvert usuarioDtoConvert;

	@Autowired
	private UsuarioService usuarioService;

	@Security(enabled = false)
	@PostMapping("/login")
	public @ResponseBody UsuarioDto login(@RequestBody UsuarioLoginJson usuarioJson) {
		try {


			String payload = usuarioJson.getSetor().concat(usuarioJson.getSenha().concat(usuarioJson.getCpf()));

			if (Encrypt.generateHash(payload).equals(usuarioJson.getCode())){					
				return usuarioService.autenticarUsuario(usuarioJson);
			 }else {
				 throw new Exception("Usuário Inválido");
			 }

		} catch (Exception e) {
			throw new EntidadeNaoEncontradaException(e.getMessage());
		}
	}

	@Security
	@PostMapping("/")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody UsuarioDto loadByCpf(@RequestBody UsuarioJson usuarioJson) {
		try {
			Usuario usuario = usuarioJsonConvert.toDomainObject(usuarioJson);
			var usuarioDomain = usuarioService.localizarUsuarioPorCpf(usuario.getCpf());
			
			var usuarioDto = usuarioDtoConvert.toDto(usuarioDomain);
			
			return usuarioDto;
		} catch (Exception e) {
			throw new ErroAoSalvarUsuarioException(e.getMessage());
		}
	}

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

	@Security	
	@GetMapping("/listar/validar")
	public @ResponseBody List<UsuarioDto> recuperarUsuarios(){
		try {
			List<UsuarioDto> users = usuarioService.getAllUsers();
			return users;
		}catch(Exception e) {
			throw new EntidadeNaoEncontradaException(e.getMessage());			
		}
	}

	@Security	
	@GetMapping("/validar/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void adicionar(@PathVariable(name = "id") Long id) {
		try {
			usuarioService.validaUsuario(id);
		} catch (Exception e) {
			throw new ErroAoSalvarUsuarioException(e.getMessage());
		}
	}

	@Security
	@GetMapping("/desativar/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void desativar(@PathVariable(name = "id") Long id) {
		try {
			usuarioService.ativarDesativarUsuario(id);
		} catch (Exception e) {
			throw new ErroAoSalvarUsuarioException(e.getMessage());
		}
	}

	@Security
	@GetMapping("/ativar/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void ativar(@PathVariable(name = "id") Long id) {
		try {
			usuarioService.ativarDesativarUsuario(id);
		} catch (Exception e) {
			throw new ErroAoSalvarUsuarioException(e.getMessage());
		}
	}

	@Security
	@GetMapping(value = "/recuperar/{cpf}")
	@ApiOperation(hidden = false, value = "Recuperar senha do usuario.")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody void recuperarSenha(
			@ApiParam(value = "CPF do usuario", required = true) @PathVariable("cpf") String cpf) {
		try {
			usuarioService.recupararSenha(cpf);
		} catch (Exception e) {
			throw new UsuarioNaoEncontradoException(cpf);
		}
	}

	@Security
	@PutMapping("/alterar")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody UsuarioDto alterarSenha(@RequestBody UsuarioJson usuarioJson) {
		try {
			Usuario usuario = usuarioJsonConvert.toDomainObject(usuarioJson);
			usuarioService.alterar(usuario);
			return usuarioDtoConvert.toDto(usuario);
		} catch (Exception e) {
			throw new ErroAoSalvarUsuarioException(e.getMessage());
		}
	}
	

}
