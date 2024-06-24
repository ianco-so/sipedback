package br.rn.sesed.sides.domain.desaparecidos.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.rn.sesed.sides.domain.desaparecidos.model.Pessoa;

@Repository
public interface PessoaRepository extends CustomJpaRepository<Pessoa, Long> {

	@Query("from Pessoa p join fetch p.registros pr where p.nome = :nome")
	Pessoa findByNome(@Param("nome") String nome);

}