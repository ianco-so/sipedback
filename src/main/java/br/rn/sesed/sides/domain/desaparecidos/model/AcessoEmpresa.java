package br.rn.sesed.sides.domain.desaparecidos.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity(name = "AcessoEmpresa")
@Table(name = "acessos_empresa", schema = "dbo")
@DynamicUpdate
@DynamicInsert
public class AcessoEmpresa {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="dt_registro")
	private String dataRegistro;
	
	@ManyToOne
    @JoinColumn(name="ce_usuario", nullable=false)
	private Usuario usuario;
	
	@Column(name="st_cpf")
	private String cpf;
	

	
	
	
}
