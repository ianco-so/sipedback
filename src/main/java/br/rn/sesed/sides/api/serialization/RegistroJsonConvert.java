package br.rn.sesed.sides.api.serialization;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.rn.sesed.sides.api.model.json.RegistroJson;
import br.rn.sesed.sides.domain.model.Registro;

@Component
public class RegistroJsonConvert {

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	ObjectMapper objectMapper;
	
	public Registro toDomainObject(RegistroJson registroJson ) {
		return modelMapper.map(registroJson, Registro.class);
	}
	
	public RegistroJson toJsonObject(String registro) throws JsonMappingException, JsonProcessingException {
		return objectMapper.readValue(registro, RegistroJson.class);
	}
	
	public void copyToDomainObject(RegistroJson registroJson, Registro registro) {
		modelMapper.map(registroJson, registro);
	}
	
}
