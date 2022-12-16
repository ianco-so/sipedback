package br.rn.sesed.sides.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.rn.sesed.sides.domain.exception.ErroAoSalvarUsuario;
import br.rn.sesed.sides.domain.exception.UsuarioNaoEncontradoException;
import br.rn.sesed.sides.domain.model.Usuario;
import br.rn.sesed.sides.domain.repository.UsuarioRepository;
import br.rn.sesed.sides.exception.SidesException;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public Usuario localizarUsuario(Long usuarioId) {
		
		return usuarioRepository.findById(usuarioId).orElseThrow(() -> new UsuarioNaoEncontradoException(usuarioId));
		
	}
	
	public Usuario localizarUsuarioPorNome(String usuarioNome) {
		try {
			if (!usuarioNome.isBlank()) {
				return usuarioRepository.findByNome(usuarioNome).get();	
			}else {
				throw new SidesException("Nome de usuario não pode ser nulo ou vazio");
			}
		}catch (Exception e) {
			throw new UsuarioNaoEncontradoException(null,usuarioNome);
		}
	}	
	
	public Usuario localizarUsuarioPorId(Long usuarioId) {
		try {			
			return usuarioRepository.findById(usuarioId).get();
		}catch (Exception e) {			
			throw new UsuarioNaoEncontradoException(usuarioId);
		}		
	}

	public Usuario salvar(Usuario usuario) {
		try {
			if (usuario != null) {
				usuarioRepository.save(usuario);				
			}
		}catch (Exception e) {
			throw new ErroAoSalvarUsuario(usuario.getNome());
		}
		return null;
	}

}
