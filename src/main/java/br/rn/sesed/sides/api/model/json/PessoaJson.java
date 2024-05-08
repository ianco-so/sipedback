package br.rn.sesed.sides.api.model.json;

import java.util.Date;

import lombok.Data;

@Data
public class PessoaJson {

	private String nome;
	
	private String identidadeGenero;
	
	private Boolean boNomeSocial;
	
	private String NomeSocial;
	
	private Date dataNascimento;
	
	private int idadeAproximada;

	private String rg;

	private String ufrg;

	private String orgaoEmissorRg;

	private String cpf;

	private String nacionalidade;

	private String naturalidade;

	private String ufNaturalidade;

	private String pai;

	private String mae;

	private String logradouro;

	private String numero;

	private String complemento;

	private String bairro;

	private String uf;

	private String municipio;

	private String escolaridade;

	private Boolean boInstituicaoEnsino;

	private String instituicaoEnsino;

	private String celular;

	private String tipoSanguineo;

	private String altura;

	private String biotipo;

	private String corPele;

	private String corOlhos;

	private String cabelo;

	private String corCabelo;

	private String corteCabelo;

	private String acessorios;

	private Boolean boDeficiente;

	private Boolean boVisual;

	private Boolean boAuditiva;

	private Boolean boAparelhoAuditivo;

	private Boolean boLibras;

	private Boolean boIntelectual;

	private String intelectual;

	private Boolean boFisica;
	
	private String fisica;

	private Boolean boCadeirante;

	private Boolean boMuleta;

	private Boolean boNeuroDesenvolvimento;

	private String neuroDesenvolvimento;

	private Boolean boSenil;

	private Boolean boMemoria;

	private String memoria;

	private Boolean boComorbidade;
	
	private String comorbidade;

	private Boolean boInteracaosocial;

	private String interacaosocial;

	private String medicacao;

	private String drogas;

	private Boolean boDividasNarcotraficantes;

	private Boolean boDividasAgiotas;

	private String situacaoEconomica;

	private String estadoPsiquico;

	private Boolean boHistoricoDesaparecimento;

	private String marcas;

	private String fotoPrincipal;
	
	private String segundaFoto;
	
	private String terceiraFoto;
}
