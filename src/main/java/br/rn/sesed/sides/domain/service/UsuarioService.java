package br.rn.sesed.sides.domain.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.rn.sesed.sides.api.model.dto.UsuarioDto;
import br.rn.sesed.sides.api.model.json.UsuarioLoginJson;
import br.rn.sesed.sides.api.serialization.UsuarioDtoConvert;
import br.rn.sesed.sides.core.security.Encrypt;
import br.rn.sesed.sides.core.security.GenerateToken;
import br.rn.sesed.sides.domain.exception.EntidadeNaoEncontradaException;
import br.rn.sesed.sides.domain.exception.ErroAoSalvarUsuarioException;
import br.rn.sesed.sides.domain.exception.UsuarioNaoEncontradoException;
import br.rn.sesed.sides.domain.model.Usuario;
import br.rn.sesed.sides.domain.repository.UsuarioRepository;
import br.rn.sesed.sides.exception.SidesException;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioDtoConvert usuarioDtoConvert;
	
	@Autowired
	private GenerateToken generateToken;
	
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public UsuarioDto autenticarUsuario(UsuarioLoginJson usuarioJson) {
		try {
			usuarioJson.setCpf(usuarioJson.getCpf().replace(".", "").replace("-", ""));
			Optional<Usuario> usuario = usuarioRepository.findByCpfAndSenha(usuarioJson.getCpf(), Encrypt.getMD5(usuarioJson.getSenha()));
			if (usuario.isPresent()) {
				UsuarioDto resultDto = usuarioDtoConvert.toDto(usuario.get());
				resultDto.setToken(generateToken.gerarToken(usuario.get().getNome(),usuario.get().getCpf() , usuario.get().getEmail()));
				return resultDto;
			}else {
				throw new UsuarioNaoEncontradoException("Usuario ou Senha inválidos, tente novamente.");
			}
		} catch (Exception e) {
			throw new SidesException(e.getMessage());
		}
	}
		
	
	public Usuario localizarUsuarioPorNome(String usuarioNome) {
		try {
			if (!usuarioNome.isBlank()) {
				return usuarioRepository.findByNome(usuarioNome).get();	
			}else {
				throw new SidesException("Nome de usuario não pode ser nulo ou vazio");
			}
		}catch (Exception e) {
			throw new UsuarioNaoEncontradoException(1L,usuarioNome);
		}
	}	
	
	public Usuario localizarUsuarioPorId(Long usuarioId) {
		try {			
			return usuarioRepository.findById(usuarioId).get();
		}catch (Exception e) {			
			throw new UsuarioNaoEncontradoException(usuarioId);
		}		
	}
	
	public Usuario localizarUsuarioPorCpf(String usuarioCpf) {
		try {			
			
			Usuario user = usuarioRepository.findByCpf(usuarioCpf).get();
			
			return user;
		}catch (Exception e) {			
			throw new UsuarioNaoEncontradoException(usuarioCpf);
		}		
	}

	public void salvar(Usuario usuario) {
		try {
			Optional<Usuario> user = usuarioRepository.findByCpfOrEmail(usuario.getCpf(), usuario.getEmail());
			if(!user.isPresent()) {
				usuario.setCpf(usuario.getCpf().replace(".", "").replace("-", ""));
				usuario.setSenha(Encrypt.getMD5(usuario.getSenha()));
				usuarioRepository.save(usuario);
			}else {
				String msg = "Um registro já existe com estes dados.";
				if(user.get().getCpf().equals(usuario.getCpf()) && user.get().getEmail().equals(usuario.getEmail())){
					msg = "Já existe um usuário cadastrado com este CPF e EMAIL. ";
				}else if(user.get().getCpf().equals(usuario.getCpf())) {
					msg = "Já existe um usuário cadastrado com este CPF. ";
				}else if(user.get().getEmail().equals(usuario.getEmail())) {
					msg = "Já existe um usuário cadastrado com este EMAIL. ";
				}				
				throw new ErroAoSalvarUsuarioException(msg);
			}
		}catch (Exception e) {
			throw e;
		}
	}


	public Boolean recupararSenha(String cpf) {
		try {		
			if(usuarioRepository.findByCpf(cpf).isPresent()) {			
				return true;
			}
			
			throw new EntidadeNaoEncontradaException(cpf);

		}catch (Exception e) {			
			throw e;
		}
	}


	public void alterar(Usuario usuario) {
		try {
			Optional<Usuario> user = usuarioRepository.findByCpf(usuario.getCpf());
			if(user.isPresent()) {
				
				String encriptedPassword = Encrypt.getMD5(usuario.getSenha());

				user.get().setSenha(encriptedPassword);

				usuarioRepository.save(user.get());
				
			}else {		
				throw new ErroAoSalvarUsuarioException("Verifique usuario");
			}
		}catch (Exception e) {
			throw e;
		}
		
	}
	
}
