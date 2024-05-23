package br.rn.sesed.sides.domain.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import br.rn.sesed.sides.domain.model.RegistroVinculado;

@Repository
public interface RegistroVinculadoRepository extends CustomJpaRepository<RegistroVinculado, Long>, JpaSpecificationExecutor<RegistroVinculado>,
		PagingAndSortingRepository<RegistroVinculado, Long> {
	
}