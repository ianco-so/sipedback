package br.rn.sesed.sides.domain.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.rn.sesed.sides.domain.model.Usuario;
import br.rn.sesed.sides.domain.repository.UsuarioRepository;
import br.rn.sesed.sides.infrastructure.authentication.JWTModel.DetalhesUsuarioData;

@Service
public class DetalhesUsuarioServiceImpl implements UserDetailsService {
	
	@Autowired
	private  UsuarioRepository usuarioRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Usuario> user = usuarioRepository.findByCpf(username);
		if (user.isEmpty()) {
			throw new UsernameNotFoundException("Usuario: ["+ username +"] não encontrado");
		}			
		return new DetalhesUsuarioData(user);
	}

}
