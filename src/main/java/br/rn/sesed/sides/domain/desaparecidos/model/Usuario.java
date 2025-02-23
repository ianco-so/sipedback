package br.rn.sesed.sides.domain.desaparecidos.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity(name = "Usuario")
@Table(name = "usuarios", schema = "dbo")
@DynamicUpdate
@DynamicInsert
public class Usuario {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="st_nome")
	private String nome;
	
	@Column(name="st_cpf")
	private String cpf;

	@Column(name="st_cnpj")
	private String cnpj;
	
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
	
	@Column(name = "bo_validado")
	private Boolean boValidado;
	
	@Column(name = "st_instituicao")
	private String instituicao;
	
	@Column(name = "bo_ativo")
	private Boolean boAtivo;

	@OneToMany(mappedBy="usuario", fetch = FetchType.LAZY)
	List<AcessoEmpresa> acessosEmpresa = new ArrayList<>();
	
}
