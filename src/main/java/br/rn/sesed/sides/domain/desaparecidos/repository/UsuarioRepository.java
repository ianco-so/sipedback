package br.rn.sesed.sides.domain.desaparecidos.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import br.rn.sesed.sides.domain.desaparecidos.model.Usuario;

@Repository
public interface UsuarioRepository extends CustomJpaRepository<Usuario, Long> {
	
	public Optional<Usuario> findByCpf(String cpf);
	
	public Optional<Usuario> findById(Long id);
	
	public Optional<Usuario> findByNome(String nome);
	
	public Optional<Usuario> findByCpfOrEmail(String cpf, String email);

	public Optional<Usuario> findByCpfAndSenha(String cpf, String senha);
	
	public List<Usuario> findAllByBoValidado(Boolean boValidado);
	
}