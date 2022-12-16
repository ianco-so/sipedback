package br.rn.sesed.sides.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.rn.sesed.sides.domain.exception.UsuarioNaoEncontradoException;
import br.rn.sesed.sides.domain.model.Usuario;
import br.rn.sesed.sides.domain.repository.UsuarioRepository;
import br.rn.sesed.sides.exception.SidesException;

@Service
public class CadastroUsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public Usuario localizarUsuario(Long usuarioId) {
		
		return usuarioRepository.findById(usuarioId).orElseThrow(() -> new UsuarioNaoEncontradoException(usuarioId));
		
	}
	
	public void salvar(Usuario usuario) {
		try {
			if (usuario != null) {
				usuarioRepository.save(usuario);
			}
		} catch (Exception e) {
			throw new SidesException("Não foi possivel salvar o usuario");
		}
	}
}