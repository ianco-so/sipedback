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
import br.rn.sesed.sides.core.security.GenerateToken;
import br.rn.sesed.sides.domain.service.UsuarioService;
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
	
//	@GetMapping("/")
//	public String login() {
//		return "";
//	}	
	
	@GetMapping("/version")
	public BuildProperties version() {
		return buildProperties;
	}	

	@PostMapping("/auth")
	public String login(@RequestBody UsuarioLoginJson usuario) throws Exception {
		try {

			UsuarioDto user = ldapService.login(orgaoLogin, usuario.getCpf(), usuario.getSenha(), orgaoDomain);
			var token = generateToken.gerarToken(user.getNome(), user.getCpf(), user.getEmail());

			return token;

		} catch (Exception e) {
			log.error(e.getMessage());
			throw new Exception("Usuário inválido");
		}
	}
}
