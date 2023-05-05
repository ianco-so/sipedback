package br.rn.sesed.sides.api.model.json;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class RegistroMultiPartJson {
	
	private RegistroJson registro;
	private MultipartFile fotoPrincipal;
	private MultipartFile segundaFoto;
	private MultipartFile terceiraFoto;
	
}