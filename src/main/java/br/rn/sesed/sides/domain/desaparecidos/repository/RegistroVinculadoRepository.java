package br.rn.sesed.sides.domain.desaparecidos.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.rn.sesed.sides.domain.desaparecidos.model.RegistroVinculado;


@Repository
public interface RegistroVinculadoRepository extends CustomJpaRepository<RegistroVinculado, Long>, JpaSpecificationExecutor<RegistroVinculado> {

    @Query("from RegistroVinculado rv join fetch rv.registroInstituicao ri where rv.registroBoletim.id = :id")
    Optional<RegistroVinculado> findById(Long id);


	
}