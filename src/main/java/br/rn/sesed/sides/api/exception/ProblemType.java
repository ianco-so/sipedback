package br.rn.sesed.sides.api.exception;

import lombok.Getter;

@Getter
public enum ProblemType {
	DADOS_INVALIDOS("/dados-invalidos", "Dados inválidos"),
	RECURSO_NAO_ENCONTRADO("/recurso-nao-encontrado", "Recurso não encontrado");
	
	private String title;
	private String uri;
	
	ProblemType(String path, String title) {
		this.uri = "https://www8.defesasocial.rn.gov.br" + path;
		this.title = title;
	}
}
