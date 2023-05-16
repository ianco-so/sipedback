package br.rn.sesed.sides.api.serialization;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.rn.sesed.sides.api.model.dto.RegistroDto;
import br.rn.sesed.sides.api.model.dto.RegistroSimpleDto;
import br.rn.sesed.sides.domain.model.Registro;

@Component
public class RegistroDtoConvert {

	@Autowired
	private ModelMapper modelMapper;

	public RegistroDto toDto(Registro registro) {
		
		
		RegistroDto registroDto = modelMapper.map(registro, RegistroDto.class); 
		
		
		return registroDto;
	}
	
	public RegistroSimpleDto toSimpleDto(Registro registro) {
		
		
		RegistroSimpleDto registroSimpleDto = modelMapper.map(registro, RegistroSimpleDto.class); 
		
		
		return registroSimpleDto;
	}

	public List<RegistroDto> toCollectionModel(List<Registro> registros) {
		
		if(this.modelMapper.getTypeMap(Registro.class, RegistroDto.class) == null) {
		
			TypeMap<Registro, RegistroDto> propertyMapper = this.modelMapper.createTypeMap(Registro.class, RegistroDto.class);
	    	propertyMapper.addMappings(mapper -> mapper.skip(RegistroDto::setPessoas));
		}
		
		List<RegistroDto> registroDtos = registros.stream()
				.map(registro -> toDto(registro))
				.collect(Collectors.toList());
		
		return registroDtos;
	}

	public List<RegistroSimpleDto> toSimpleCollectionModel(List<Registro> registros) {
		try {			
			List<RegistroSimpleDto> registroDtos = registros.stream()
															.map(registro -> toSimpleDto(registro))
															.collect(Collectors.toList());
			return registroDtos;
		} catch (Exception e) {
			throw e;
		}
	}

}
