package br.rn.sesed.sides.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity(name = "Usuario")
@Table(name = "usuarios", schema = "dbo")
@DynamicUpdate
public class Usuario {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="st_nome")
	private String nome;
	
	@Column(name="st_cpf")
	private String cpf;
	
	@Column(name="st_fone_contato")
	private String telefone;
	
	@Column(name="st_email")
	private String email;
	
	@Column(name="st_senha")
	private String senha;
	
	@Column(name="dt_cadastro")
	private String cadastro;
	
	@Column(name="st_tipo")
	private String tipo;
	
	@Column(name="st_revalida")
	private String tokenFcm;
	
	@Column(name="bo_token_enable")
	private Boolean tokenEnable;
	
	@Column(name="bo_senha_reset")
	private Boolean senhaReset;
	
}
