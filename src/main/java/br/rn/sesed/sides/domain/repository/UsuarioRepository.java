package br.rn.sesed.sides.domain.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import br.rn.sesed.sides.domain.model.Usuario;

@Repository
public interface UsuarioRepository extends CustomJpaRepository<Usuario, Long> {
	
	public Optional<Usuario> findById(String nome);
	public Optional<Usuario> findByNome(String nome);

}
