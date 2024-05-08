package br.rn.sesed.sides.api.model.json;

import java.util.Date;

import lombok.Data;

@Data
public class RegistroJson {

	
	public String cpfUsuario;

	public PessoaJson pessoa;
	
	public Long tipoRegistro;

	public String boletim;

	public String delegacia;

	public Date dataBoletim;
	
	public Date dataRegistro;

	public Date dataDesaparecimento;

	public String cep;

	public String uf;

	public String municipio;

	public String logradouro;

	public String numero;

	public String complemento;

	public String bairro;

	public String pontoReferencia;

	public String detalhes;

	public String lat;

	public String lon;

	public String nomeComunicante;

	public String nomeSocialComunicante;

	public String cpfComunicante;

	public String rgComunicante;

	public String rgOrgaoEmissorComunicante;

	public String ufRgComunicante;

	public String telefoneComunicante;

	public String emailComunicante;

	public String relacacoVitima;

}
