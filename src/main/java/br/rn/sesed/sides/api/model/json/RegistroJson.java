package br.rn.sesed.sides.api.model.json;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import lombok.Data;

@Data
public class RegistroJson {

	private Long id;
	
	@JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm")
	public LocalDateTime dataBoletim;
	
	@JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm")
	public LocalDateTime dataRegistro;

	@JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm")
	public LocalDateTime dataDesaparecimento;
	
	public String cpfUsuario;

	public PessoaJson pessoa;
	
	public Long tipoRegistro;

	public boolean vinculado;

	public String boletim;

	public String delegacia;

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

	public void setCpfComunicante(String cpfComunicante){
		if(cpfComunicante != null)
			this.cpfComunicante = cpfComunicante.replaceAll("\\.|-|/", "");
	}

}
