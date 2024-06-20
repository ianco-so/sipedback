package br.rn.sesed.sides.api.serialization;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.rn.sesed.sides.api.model.dto.RegistroVinculadoDto;
import br.rn.sesed.sides.domain.model.RegistroVinculado;

@Component
public class RegistroVinculadoDtoConvert {

	@Autowired
	private ModelMapper modelMapper;

	public RegistroVinculadoDto toDto(RegistroVinculado registroVinculado) {
		
		
		RegistroVinculadoDto registroVinculadoDto = modelMapper.map(registroVinculado, RegistroVinculadoDto.class); 
		
		
		return registroVinculadoDto;
	}
	
	


}
