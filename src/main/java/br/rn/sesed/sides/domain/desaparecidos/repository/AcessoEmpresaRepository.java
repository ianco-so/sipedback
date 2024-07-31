package br.rn.sesed.sides.domain.desaparecidos.repository;

import org.springframework.stereotype.Repository;

import br.rn.sesed.sides.domain.desaparecidos.model.AcessoEmpresa;

@Repository
public interface AcessoEmpresaRepository extends CustomJpaRepository<AcessoEmpresa, Long> {
	

}