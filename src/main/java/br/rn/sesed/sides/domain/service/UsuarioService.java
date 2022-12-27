package br.rn.sesed.sides.domain.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.rn.sesed.sides.domain.exception.ErroAoSalvarUsuarioException;
import br.rn.sesed.sides.domain.exception.UsuarioInvalidoException;
import br.rn.sesed.sides.domain.exception.UsuarioNaoEncontradoException;
import br.rn.sesed.sides.domain.model.Usuario;
import br.rn.sesed.sides.domain.repository.UsuarioRepository;
import br.rn.sesed.sides.exception.SidesException;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
		
	
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

	public void salvar(Usuario usuario) {
		try {
			Optional<Usuario> user = usuarioRepository.findByCpfOrEmail(usuario.getCpf(), usuario.getEmail());
			if(!user.isPresent()) {
				//usuario.setSenha(bCryptPasswordEncoder().encode(usuario.getSenha()));
				usuario.setCpf(usuario.getCpf().replace(".", "").replace("-", ""));
				//Date datacadastro = Calendar.getInstance().getTime();
				//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss:SSSZ");
				//usuario.setCadastro(sdf.format(datacadastro));
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
	
}
