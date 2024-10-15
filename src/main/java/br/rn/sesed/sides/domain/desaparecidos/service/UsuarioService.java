package br.rn.sesed.sides.domain.desaparecidos.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import br.rn.sesed.sides.api.model.dto.UsuarioDto;
import br.rn.sesed.sides.api.model.json.UsuarioLoginJson;
import br.rn.sesed.sides.api.serialization.UsuarioDtoConvert;
import br.rn.sesed.sides.core.generator.RandomStringGeneratorService;
import br.rn.sesed.sides.core.security.Encrypt;
import br.rn.sesed.sides.core.security.GenerateToken;
import br.rn.sesed.sides.domain.desaparecidos.model.AcessoEmpresa;
import br.rn.sesed.sides.domain.desaparecidos.model.Usuario;
import br.rn.sesed.sides.domain.desaparecidos.repository.AcessoEmpresaRepository;
import br.rn.sesed.sides.domain.desaparecidos.repository.UsuarioRepository;
import br.rn.sesed.sides.domain.exception.EntidadeNaoEncontradaException;
import br.rn.sesed.sides.domain.exception.ErroAoSalvarUsuarioException;
import br.rn.sesed.sides.domain.exception.UsuarioNaoEncontradoException;
import br.rn.sesed.sides.domain.exception.UsuarioNaoValidado;
import br.rn.sesed.sides.domain.rotafx.model.Notificacao;
import br.rn.sesed.sides.domain.rotafx.service.NotificacaoService;
import br.rn.sesed.sides.exception.SidesException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UsuarioService {

	@Autowired
	private UsuarioDtoConvert usuarioDtoConvert;

	@Autowired
	private GenerateToken generateToken;

	@Autowired
	private RandomStringGeneratorService stringGeneratorService;

	@Autowired
	private NotificacaoService notificacaoService;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private AcessoEmpresaRepository acessoEmpresaRepository;

	@Autowired
	private SpringTemplateEngine templateEngine;

	public UsuarioDto autenticarUsuario(UsuarioLoginJson usuarioJson) {
		try {
			Optional<Usuario> usuario;
			usuarioJson.setCpf(usuarioJson.getCpf().replace(".", "").replace("-", ""));
			if (usuarioJson.getCnpj() != null) {
				usuarioJson.setCnpj(usuarioJson.getCnpj().replace(".", "").replace("-", "").replace("/", ""));
				usuario = usuarioRepository.findByCnpjAndSenha(usuarioJson.getCnpj(), Encrypt.getMD5(usuarioJson.getSenha()));
				var novoAcesso = new AcessoEmpresa();
				novoAcesso.setUsuario(usuario.get());
				novoAcesso.setCpf(usuarioJson.getCpf());
				acessoEmpresaRepository.save(novoAcesso);
				if (usuario.isPresent()) {
					if (usuario.get().getBoValidado()) {
						UsuarioDto resultDto = usuarioDtoConvert.toDto(usuario.get());
						resultDto.setNome(usuarioJson.getNome());
						resultDto.setCpf(usuarioJson.getCpf());
						resultDto.setToken(generateToken.gerarToken(usuario.get().getNome(), usuario.get().getCnpj(), usuario.get().getEmail()));
						return resultDto;
					} else {
						throw new UsuarioNaoValidado();
					}
				} else {
					throw new UsuarioNaoEncontradoException("Usuario ou Senha inválidos, tente novamente.");
				}
			} else {
				usuario = usuarioRepository.findByCpfAndSenha(usuarioJson.getCpf(), Encrypt.getMD5(usuarioJson.getSenha()));
				if (usuario.isPresent()) {
					if (usuario.get().getBoValidado()) {
						UsuarioDto resultDto = usuarioDtoConvert.toDto(usuario.get());
						resultDto.setToken(generateToken.gerarToken(usuario.get().getNome(), usuario.get().getCpf(), usuario.get().getEmail()));
						return resultDto;
					} else {
						throw new UsuarioNaoValidado();
					}
				} else {
					throw new UsuarioNaoEncontradoException("Usuario ou Senha inválidos, tente novamente.");
				}
			}
			
			
		} catch (Exception e) {
			throw new SidesException(e.getMessage());
		}
	}

	public void validaUsuario(Long id) {
		try {
			if (id != null) {
				Usuario user = usuarioRepository.findById(id).get();
				user.setBoValidado(true);
				usuarioRepository.save(user);
			} else {
				throw new SidesException("O idd de usuario não pode ser nulo ou vazio");
			}
		} catch (NoSuchElementException e) {
			throw new UsuarioNaoEncontradoException(id);
		} catch (Exception e) {
			throw new SidesException(e.getMessage());
		}
	}

	public void ativarDesativarUsuario(Long id) {
		try {
			if (id != null) {
				Usuario user = usuarioRepository.findById(id).get();
				user.setBoAtivo(!user.getBoAtivo());
				usuarioRepository.save(user);
			} else {
				throw new SidesException("O id de usuario não pode ser nulo ou vazio");
			}
		} catch (NoSuchElementException e) {
			throw new UsuarioNaoEncontradoException(id);
		} catch (Exception e) {
			throw new SidesException(e.getMessage());
		}
	}

	public Usuario localizarUsuarioPorNome(String usuarioNome) {
		try {
			if (!usuarioNome.isBlank()) {
				return usuarioRepository.findByNome(usuarioNome).get();
			} else {
				throw new SidesException("Nome de usuario não pode ser nulo ou vazio");
			}
		} catch (Exception e) {
			throw new UsuarioNaoEncontradoException(1L, usuarioNome);
		}
	}

	public Usuario localizarUsuarioPorId(Long usuarioId) {
		try {
			return usuarioRepository.findById(usuarioId).get();
		} catch (Exception e) {
			throw new UsuarioNaoEncontradoException(usuarioId);
		}
	}

	public Usuario localizarUsuarioPorCpf(String usuarioCpf) {
		try {

			Usuario user = usuarioRepository.findByCpf(usuarioCpf).get();

			return user;
		} catch (Exception e) {
			throw new UsuarioNaoEncontradoException(usuarioCpf);
		}
	}

	public void salvar(Usuario usuario) {
		try {
			Optional<Usuario> user = usuarioRepository.findByCpfOrEmail(usuario.getCpf(), usuario.getEmail());
			if (!user.isPresent()) {
				usuario.setCpf(usuario.getCpf().replace(".", "").replace("-", ""));
				usuario.setSenha(Encrypt.getMD5(usuario.getSenha()));
				usuarioRepository.save(usuario);
			} else {
				String msg = "Um registro já existe com estes dados.";
				if (user.get().getCpf().equals(usuario.getCpf()) && user.get().getEmail().equals(usuario.getEmail())) {
					msg = "Já existe um usuário cadastrado com este CPF e EMAIL. ";
				} else if (user.get().getCpf().equals(usuario.getCpf())) {
					msg = "Já existe um usuário cadastrado com este CPF. ";
				} else if (user.get().getEmail().equals(usuario.getEmail())) {
					msg = "Já existe um usuário cadastrado com este EMAIL. ";
				}
				throw new ErroAoSalvarUsuarioException(msg);
			}
		} catch (Exception e) {
			throw e;
		}
	}

	@Transactional
	public Usuario atualizar(Usuario usuario) {
		return usuarioRepository.save(usuario);
	}

	public Boolean recupararSenha(String cpf) {
		try {

			Optional<Usuario> usuario = usuarioRepository.findByCpf(cpf);

			if (usuario.isPresent()) {

				var novaSenha = stringGeneratorService.generateRandomString();

				usuario.get().setSenha(Encrypt.getMD5(novaSenha));

				this.atualizar(usuario.get());

				Map<String, Object> templateModel = new HashMap<>();
				Context context = new Context();
				context.setVariables(templateModel);
				String htmlTemplate = templateEngine.process("emailTemplate", context);
				htmlTemplate = htmlTemplate.replace("*senha*", novaSenha);

				var notificacao = new Notificacao();
				notificacao.setPessoaDestino(cpf);
				notificacao.setDestino(usuario.get().getEmail());
				notificacao.setOrigem("SIPED");
				notificacao.setPlataformaEnvio("EMAIL");
				notificacao.setTipo("RecuperarSenha");
				notificacao.setTitulo("Recuperação de Senha");
				notificacao.setCorpo(htmlTemplate);
				notificacaoService.save(notificacao);

				return true;
			}

			throw new EntidadeNaoEncontradaException(cpf);

		} catch (Exception e) {
			throw e;
		}
	}

	public void alterar(Usuario usuario) {
		try {
			Optional<Usuario> user = usuarioRepository.findByCpf(usuario.getCpf());
			if (user.isPresent()) {

				String encriptedPassword = Encrypt.getMD5(usuario.getSenha());

				user.get().setSenha(encriptedPassword);

				usuarioRepository.save(user.get());

			} else {
				throw new ErroAoSalvarUsuarioException("Verifique usuario");
			}
		} catch (Exception e) {
			throw e;
		}

	}

	public List<UsuarioDto> getAllUsers() {
		try {
			// List<Usuario> ususariosnaovalidados =
			// usuarioRepository.findAllByBoValidado(false);
			List<Usuario> ususarios = usuarioRepository.findAll();
			List<UsuarioDto> usersDto = usuarioDtoConvert.toCollectionModel(ususarios);
			return usersDto;
		} catch (Exception e) {
			throw e;
		}
	}

}
