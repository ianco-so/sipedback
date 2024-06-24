package br.rn.sesed.sides.domain.desaparecidos.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.rn.sesed.sides.domain.desaparecidos.model.Registro;

@Repository
public interface RegistroRepository extends CustomJpaRepository<Registro, Long>, JpaSpecificationExecutor<Registro>,
		PagingAndSortingRepository<Registro, Long> {

//	@EntityGraph(value = "Registro.pessoasDetail", type = EntityGraphType.LOAD) // Mecanismo para os Fetch no atributo pessoas
	@Query("from Registro r join fetch r.pessoas rp where r.id = :id")
	public Optional<Registro> findById(@Param("id") Long id);

	@Query("from Registro r left join fetch r.registrosInstituicoes ri where r.id = :id")
	public Optional<Registro> findBoletimVinculadoById(@Param("id") Long id);

	@Query("from Registro r join fetch r.pessoas rp where r.usuario.cpf = :cpf")
	public List<Registro> findByCpfComunicante(@Param("cpf") String cpfComunicante);
	
	@Query("from Registro r where r.usuario.cpf = :cpf")
	public List<Registro> findBySimpleCpfComunicante(@Param("cpf") String cpfComunicante);

	@Query("select r.id, r.boletim, r.nomeComunicante, r.pessoas from Registro r")
    public List<Registro> findAllPesquisa();

    public List<Registro> findByTipoRegistroAndVinculado(Long tipoRegistro, boolean vinculado); 



	
//	public List<Registro> findByPessoas(List<Pessoa> pessoas);
	
}