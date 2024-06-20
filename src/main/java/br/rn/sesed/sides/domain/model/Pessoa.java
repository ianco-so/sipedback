package br.rn.sesed.sides.domain.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PostLoad;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import br.rn.sesed.sides.api.model.json.FileBase64;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@Entity(name = "Pessoa")
@Table(name = "pessoas", schema = "dbo")
@DynamicUpdate
@DynamicInsert
public class Pessoa {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "st_nome")
	private String nome;

	@Column(name = "st_identidadegenero")
	private String identidadeGenero;

	@Column(name = "bo_nomesocial")
	private Boolean boNomeSocial;

	@Column(name = "st_nomesocial")
	private String nomeSocial;

	@Column(name = "dt_nascimento")
	private LocalDate dataNascimento;

	@Column(name = "nu_idadeaproximada")
	private Integer idadeAproximada;

	@Column(name = "st_rg")
	private String rg;

	@Column(name = "st_ufrg")
	private String ufrg;

	@Column(name = "st_orgaoemissorrg")
	private String orgaoEmissorRg;

	@Column(name = "st_cpf")
	private String cpf;

	@Column(name = "st_nacionalidade")
	private String nacionalidade;

	@Column(name = "st_naturalidade")
	private String naturalidade;

	@Column(name = "st_ufnaturalidade")
	private String ufNaturalidade;

	@Column(name = "st_pai")
	private String pai;

	@Column(name = "st_mae")
	private String mae;

	@Column(name = "st_logradouro")
	private String logradouro;

	@Column(name = "st_numero")
	private String numero;

	@Column(name = "st_complemento")
	private String complemento;

	@Column(name = "st_bairro")
	private String bairro;

	@Column(name = "st_uf")
	private String uf;

	@Column(name = "st_municipio")
	private String municipio;

	@Column(name = "st_escolaridade")
	private String escolaridade;

	@Column(name = "bo_instituicaoensino")
	private Boolean boInstituicaoEnsino;

	@Column(name = "st_instituicaoensino")
	private String instituicaoEnsino;

	@Column(name = "st_celular")
	private String celular;

	@Column(name = "st_tiposanguineo")
	private String tipoSanguineo;

	@Column(name = "st_altura")
	private String altura;

	@Column(name = "st_biotipo")
	private String biotipo;

	@Column(name = "st_corpele")
	private String corPele;

	@Column(name = "st_corolhos")
	private String corOlhos;

	@Column(name = "st_cabelo")
	private String cabelo;

	@Column(name = "st_corcabelo")
	private String corCabelo;

	@Column(name = "st_cortecabelo")
	private String corteCabelo;

	@Column(name = "st_acessorios")
	private String acessorios;

	@Column(name = "bo_deficiente")
	private Boolean boDeficiente;

	@Column(name = "bo_visual")
	private Boolean boVisual;

	@Column(name = "bo_auditiva")
	private Boolean boAuditiva;

	@Column(name = "bo_aparelhoauditivo")
	private Boolean boAparelhoAuditivo;

	@Column(name = "bo_libras")
	private Boolean boLibras;

	@Column(name = "bo_intelectual")
	private Boolean boIntelectual;

	@Column(name = "st_intelectual")
	private String intelectual;

	@Column(name = "bo_fisica")
	private Boolean boFisica;

	@Column(name = "st_fisica")
	private String fisica;

	@Column(name = "bo_cadeirante")
	private Boolean boCadeirante;

	@Column(name = "bo_muleta")
	private Boolean boMuleta;

	@Column(name = "bo_neurodesenvolvimento")
	private Boolean boNeuroDesenvolvimento;

	@Column(name = "st_neurodesenvolvimento")
	private String neuroDesenvolvimento;

	@Column(name = "bo_senil")
	private Boolean boSenil;

	@Column(name = "bo_memoria")
	private Boolean boMemoria;

	@Column(name = "st_memoria")
	private String memoria;

	@Column(name = "bo_comorbidade")
	private Boolean boComorbidade;

	@Column(name = "st_comorbidade")
	private String comorbidade;

	@Column(name = "bo_interacaosocial")
	private Boolean boInteracaosocial;

	@Column(name = "st_interacaosocial")
	private String interacaosocial;

	@Column(name = "st_medicacao")
	private String medicacao;

	@Column(name = "st_drogas")
	private String drogas;

	@Column(name = "bo_dividasnarcotraficantes")
	private Boolean boDividasNarcotraficantes;

	@Column(name = "bo_dividasagiotas")
	private Boolean boDividasAgiotas;

	@Column(name = "st_situacaoeconomica")
	private String situacaoEconomica;

	@Column(name = "st_estadopsiquico")
	private String estadoPsiquico;

	@Column(name = "bo_historicodesaparecimento")
	private Boolean boHistoricoDesaparecimento;

	@Column(name = "st_marcas")
	private String marcas;

	//@Column(name = "st_fotoprincipal")
	@Transient
	private String stFotoprincipal;

	//@Column(name = "st_segundafoto")
	@Transient
	private String stSegundafoto;

	//@Column(name = "st_terceirafoto")
	@Transient
	private String stTerceirafoto;

	@Transient
	private FileBase64 fotoPrincipal;

	@Transient
	private FileBase64 segundaFoto;

	@Transient
	private FileBase64 terceiraFoto;

	@Transient
	private List<Foto> fotos;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "registro_pessoa", joinColumns = @JoinColumn(name = "ce_pessoa"), inverseJoinColumns = @JoinColumn(name = "ce_registro"))
	public List<Registro> registros;

	
	// @PrePersist
	// public void prePersist() {
	// 	if(fotoPrincipal != null && !fotoPrincipal.getContent().isEmpty()) {
	// 		this.setStFotoprincipal(fotoPrincipal.getContent());
	// 		this.stFotoprincipal = fotoPrincipal.getContent();
			
	// 	}
	// 	if(segundaFoto != null && !segundaFoto.getContent().isEmpty()) {
	// 		this.setStSegundafoto(segundaFoto.getContent());
	// 		this.stSegundafoto = segundaFoto.getContent();
	// 	}
	// 	if(terceiraFoto != null && !terceiraFoto.getContent().isEmpty()) {
	// 		this.setStTerceirafoto(terceiraFoto.getContent());
	// 		this.stTerceirafoto = terceiraFoto.getContent();
	// 	}
	// }
	@PostLoad
	public void postLoad() {

		this.stFotoprincipal = "images?imageId="+ this.id +"&imageName=fotoprincipal";
		this.stSegundafoto = "images?imageId="+ this.id +"&imageName=segundafoto";
		this.stTerceirafoto = "images?imageId="+ this.id +"&imageName=terceirafoto";


		// fotoPrincipal.setContent(this.stFotoprincipal);
		// segundaFoto.setContent(this.stSegundafoto);
		// terceiraFoto.setContent(this.stTerceirafoto);
	}
}