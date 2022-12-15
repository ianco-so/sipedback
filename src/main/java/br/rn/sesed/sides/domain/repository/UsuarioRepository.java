package br.rn.sesed.sides.domain.repository;

import org.springframework.stereotype.Repository;

import br.rn.sesed.sides.domain.model.Usuario;

@Repository
public interface UsuarioRepository extends CustomJpaRepository<Usuario, Long> {

}
