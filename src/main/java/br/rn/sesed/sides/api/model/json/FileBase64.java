package br.rn.sesed.sides.api.model.json;

import lombok.Data;

@Data
public class FileBase64 {
	private String content;
	private String fileName;
}
