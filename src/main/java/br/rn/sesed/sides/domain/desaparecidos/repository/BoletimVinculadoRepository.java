package br.rn.sesed.sides.domain.desaparecidos.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.rn.sesed.sides.domain.desaparecidos.model.BoletimVinculado;



@Repository
public interface BoletimVinculadoRepository extends CustomJpaRepository<BoletimVinculado, Long>, JpaSpecificationExecutor<BoletimVinculado> {


	@Query("from BoletimVinculado bv right join fetch bv.registrosBoletins where bv.id = :id")
	Optional<BoletimVinculado> findById(Long id);

	
}