package br.rn.sesed.sides.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.rn.sesed.sides.domain.exception.PessoaNaoEncontradaException;
import br.rn.sesed.sides.domain.exception.UsuarioNaoEncontradoException;
import br.rn.sesed.sides.domain.model.Usuario;
import br.rn.sesed.sides.domain.repository.UsuarioRepository;

@Service
public class CadastroUsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public Usuario localizarUsuario(Long usuarioId) {
		
		return usuarioRepository.findById(usuarioId).orElseThrow(() -> new UsuarioNaoEncontradoException(usuarioId));
		
	}
	
	public Usuario localizarUsuarioPorNome(String usuarioNome) {
		
		try {
		return usuarioRepository.findByNome(usuarioNome).get();
		
		}catch (Exception e) {
			throw new UsuarioNaoEncontradoException(null,usuarioNome);
		}
		 
		
	}
	
	public Usuario localizarUsuarioPorNome2(String usuarioNome) {
		
		return usuarioRepository.findByNome(usuarioNome).orElseThrow(() -> new UsuarioNaoEncontradoException(usuarioNome));
		
	}

	public Usuario salvar(Usuario usuario) {
		// TODO Auto-generated method stub
		
		try {
			
		}catch (Exception e) {
			// TODO: handle exception
		}
		
		return null;
	}

}
