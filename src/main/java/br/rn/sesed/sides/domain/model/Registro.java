package br.rn.sesed.sides.domain.model;

import java.util.Date;
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
import javax.persistence.ManyToOne;
import javax.persistence.PostLoad;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
//@NamedEntityGraph(name = "Registro.pessoasDetail", attributeNodes = @NamedAttributeNode("pessoas")) //Necessário para os Fetch no atributo pessoas
@Entity(name = "Registro")
@Table(name = "registros", schema = "dbo")
@DynamicUpdate
@DynamicInsert
public class Registro {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ce_usuario")
	public Usuario usuario;

	@Column(name = "ce_tiporegistro")
	public Long tipoRegistro;

	@Column(name = "st_boletim")
	public String boletim;

	@Transient
	private String nomeSocial;	
	
	@Column(name = "st_delegacia")
	public String delegacia;

	@Column(name = "dt_registro")
	public Date dataRegistro;
	
	@Column(name = "dt_boletim")
	public Date dataBoletim;

	@Column(name = "dt_desaparecimento")
	public Date dataDesaparecimento;

	@Column(name = "st_cep")
	public String cep;

	@Column(name = "st_uf")
	public String uf;

	@Column(name = "st_municipio")
	public String municipio;

	@Column(name = "st_logradouro")
	public String logradouro;

	@Column(name = "st_numero")
	public String numero;

	@Column(name = "st_complemento")
	public String complemento;

	@Column(name = "st_bairro")
	public String bairro;

	@Column(name = "st_pontoreferencia")
	public String pontoReferencia;

	@Column(name = "st_detalhes")
	public String detalhes;

	@Column(name = "st_lat")
	public String lat;

	@Column(name = "st_long")
	public String lon;

	@Column(name = "st_nomecomunicante")
	public String nomeComunicante;

	@Column(name = "st_nomesocialcomunicante")
	public String nomeSocialComunicante;

	@Column(name = "st_cpfcomunicante")
	public String cpfComunicante;

	@Column(name = "st_rgcomunicante")
	public String rgComunicante;

	@Column(name = "st_rgorgaoemissorcomunicante")
	public String rgOrgaoEmissorComunicante;

	@Column(name = "st_ufrgcomunicante")
	public String ufRgComunicante;

	@Column(name = "st_telefonecomunicante")
	public String telefoneComunicante;

	@Column(name = "st_emailcomunicante")
	public String emailComunicante;

	@Column(name = "st_relacaocomvitima")
	public String relacaoComVitima;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "registro_pessoa", joinColumns = @JoinColumn(name = "ce_registro"), inverseJoinColumns = @JoinColumn(name = "ce_pessoa"))
	public List<Pessoa> pessoas;
	
	@PostLoad
	public void onLoad() {
		if(pessoas != null && !pessoas.isEmpty()) {
			this.nomeSocial = pessoas.get(0).getNomeSocial();
		}
	}
	
}
