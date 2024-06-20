package br.rn.sesed.sides.api.model.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import br.rn.sesed.sides.domain.model.Registro;
import lombok.Data;

@Data
public class BoletimVinculadoDto {


	public Long id;

	public Long tipoRegistro;

	public boolean vinculado;

	public String boletim;

	public PessoaDto pessoa;

	private String NomeSocial;

	public String delegacia;
	
	@JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm")
	public LocalDateTime dataBoletim;
	
	@JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm")
	public LocalDateTime dataDesaparecimento;

	public String cep;

	public String uf;

	public String municipio;

	public String logradouro;

	public String numero;

	public String complemento;

	public String bairro;
	
	public String detalhes;

	public String lat;
	
	public String pontoReferencia;

	public String lon;

	public String nomeComunicante;

	public String nomeSocialComunicante;

	public String cpfComunicante;

	public String rgComunicante;

	public String rgOrgaoEmissorComunicante;

	public String ufRgComunicante;

	public String telefoneComunicante;

	public String emailComunicante;

	public String relacaoComVitima;

	public List<PessoaDto> pessoas;

    private List<RegistroDto> registrosInstituicoes;
	
	
}
