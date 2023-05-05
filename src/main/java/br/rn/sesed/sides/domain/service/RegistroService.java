package br.rn.sesed.sides.domain.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.rn.sesed.sides.domain.exception.EntidadeNaoEncontradaException;
import br.rn.sesed.sides.domain.model.Pessoa;
import br.rn.sesed.sides.domain.model.Registro;
import br.rn.sesed.sides.domain.repository.PessoaRepository;
import br.rn.sesed.sides.domain.repository.RegistroRepository;

@Service
public class RegistroService {


	@Autowired
	private PessoaRepository pessoaRepository;

	@Autowired
	private RegistroRepository registroRepository;

	@Autowired
	private PessoaService pessoaService;

//	public UsuarioDto autenticarUsuario(UsuarioLoginJson usuarioJson) {
//		try {
//			usuarioJson.setCpf(usuarioJson.getCpf().replace(".", "").replace("-", ""));
//			Optional<Usuario> usuario = usuarioRepository.findByCpfAndSenha(usuarioJson.getCpf(), usuarioJson.getSenha());
//			if (usuario.isPresent()) {
//				UsuarioDto resultDto = usuarioDtoConvert.toDto(usuario.get());
//				resultDto.setToken(generateToken.gerarToken(usuario.get().getNome(),usuario.get().getCpf() , usuario.get().getEmail()));
//				return resultDto;
//			}else {
//				throw new UsuarioNaoEncontradoException("Usuario ou Senha inválidos, tente novamente.");
//			}
//		} catch (Exception e) {
//			throw new SidesException(e.getMessage());
//		}
//	}
//		
//	
//	public Usuario localizarUsuarioPorNome(String usuarioNome) {
//		try {
//			if (!usuarioNome.isBlank()) {
//				return usuarioRepository.findByNome(usuarioNome).get();	
//			}else {
//				throw new SidesException("Nome de usuario não pode ser nulo ou vazio");
//			}
//		}catch (Exception e) {
//			throw new UsuarioNaoEncontradoException(1L,usuarioNome);
//		}
//	}	
//	
//	public Usuario localizarUsuarioPorId(Long usuarioId) {
//		try {			
//			return usuarioRepository.findById(usuarioId).get();
//		}catch (Exception e) {			
//			throw new UsuarioNaoEncontradoException(usuarioId);
//		}		
//	}
//	
//	public Usuario localizarUsuarioPorCpf(String usuarioCpf) {
//		try {			
//			return usuarioRepository.findByCpf(usuarioCpf).get();
//		}catch (Exception e) {			
//			throw new UsuarioNaoEncontradoException(usuarioCpf);
//		}		
//	}

	@Transactional
	public void salvar(Registro registro) {
		try {
			Pessoa pessoa = pessoaService.salvar(registro.getPessoas().get(0));
			registro.getPessoas().clear();
			registro.getPessoas().add(pessoa);
			Registro reg = registroRepository.save(registro);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	public List<Registro> localizarPorCpf(String cpf) {

		try {
			return registroRepository.findByCpfComunicante(cpf);
		} catch (Exception e) {
			throw new EntidadeNaoEncontradaException(e.getMessage());
		}
	}

	public List<Registro> localizarSimplePorCpf(String cpf) {
		try {
			return registroRepository.findBySimpleCpfComunicante(cpf);
		} catch (Exception e) {
			throw new EntidadeNaoEncontradaException(e.getMessage());
		}
	}

	public Registro localizarPorId(Long id) {

		try {
			return registroRepository.findById(id).get();
		} catch (Exception e) {
			throw new EntidadeNaoEncontradaException(e.getMessage());
		}
	}

	public List<Registro> findByPessoa(Pessoa pessoa) {
		try {	
			return pessoaRepository.findByNome(pessoa.getNome()).getRegistros();
		} catch (Exception e) {
			throw new EntidadeNaoEncontradaException(e.getMessage());
		}
	}


//	public List<Registro> localizarPorPessoas(List<Pessoa> pessoas) {
//		try {
//			return registroRepository.findByPessoas(pessoas);
//		} catch (Exception e) {
//			throw new EntidadeNaoEncontradaException(e.getMessage());
//		}
//	}

}
