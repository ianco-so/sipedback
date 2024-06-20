package br.rn.sesed.sides.api.serialization;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.rn.sesed.sides.api.model.dto.BoletimVinculadoDto;
import br.rn.sesed.sides.domain.model.BoletimVinculado;

@Component
public class BoletimVinculadoDtoConvert {

	@Autowired
	private ModelMapper modelMapper;

	public BoletimVinculadoDto toDto(BoletimVinculado registroVinculado) {
		
		
		BoletimVinculadoDto registroVinculadoDto = modelMapper.map(registroVinculado, BoletimVinculadoDto.class); 
		
		
		return registroVinculadoDto;
	}
	
	public List<BoletimVinculadoDto> toCollectionModel(List<BoletimVinculado> registros) {
		
		if(this.modelMapper.getTypeMap(BoletimVinculado.class, BoletimVinculadoDto.class) == null) {
		
			TypeMap<BoletimVinculado, BoletimVinculadoDto> propertyMapper = this.modelMapper.createTypeMap(BoletimVinculado.class, BoletimVinculadoDto.class);
	    	
		}
		
		List<BoletimVinculadoDto> registroDtos = registros.stream()
				.map(registro -> toDto(registro))
				.collect(Collectors.toList());
		
		return registroDtos;
	}



}
