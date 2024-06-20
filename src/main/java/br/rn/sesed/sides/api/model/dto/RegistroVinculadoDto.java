package br.rn.sesed.sides.api.model.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class RegistroVinculadoDto {

  public Long id;

    private LocalDateTime dataVinculacao;

    private RegistroDto registroBoletim;

    private RegistroDto registroInstituicao;
	
	
	
}
