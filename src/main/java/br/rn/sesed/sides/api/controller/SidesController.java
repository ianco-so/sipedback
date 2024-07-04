package br.rn.sesed.sides.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.info.BuildProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.rn.sesed.sides.api.model.dto.UsuarioDto;
import br.rn.sesed.sides.api.model.json.UsuarioLoginJson;
import br.rn.sesed.sides.core.ldap.LdapService;
import br.rn.sesed.sides.core.security.Encrypt;
import br.rn.sesed.sides.core.security.GenerateToken;
import br.rn.sesed.sides.core.security.annotation.Security;
import br.rn.sesed.sides.domain.desaparecidos.service.UsuarioService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/")
public class SidesController {
	
	@Value("${sides.ldap.orgao}")
	private String orgaoLogin;

	@Value("${sides.ldap.ip-domain}")
	private String orgaoDomain;

	@Autowired
	private GenerateToken generateToken;

	@Autowired
	private LdapService ldapService;


	@Autowired
	BuildProperties buildProperties;
	
	@Autowired 
	UsuarioService cadastroUsuarioService;
	
	@Security
	@GetMapping("/version")
	public BuildProperties version() {
		log.debug("Application SIDES Version: {}", buildProperties.getVersion());
		return buildProperties;
	}	

	@Security(enabled = false)
	@PostMapping("/auth")
	public String login(@RequestBody UsuarioLoginJson usuarioJson) throws Exception {
		try {

			String payload = usuarioJson.getSetor().concat(usuarioJson.getSenha().concat(usuarioJson.getCpf()));
			if (Encrypt.generateHash(payload).equals(usuarioJson.getCode())) {
				UsuarioDto user = ldapService.login(orgaoLogin, usuarioJson.getCpf(), usuarioJson.getSenha(), orgaoDomain);
				var token = generateToken.gerarToken(user.getNome(), user.getCpf(), user.getEmail());
				return token;
			} else {
				throw new Exception("Usuário Inválido");
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new Exception("Usuário inválido");
		}
	}
}
