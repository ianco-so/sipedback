package br.rn.sesed.sides.api.serialization;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.rn.sesed.sides.api.model.json.RegistroJson;
import br.rn.sesed.sides.api.model.json.RegistroMultiPartJson;
import br.rn.sesed.sides.api.model.json.UsuarioJson;
import br.rn.sesed.sides.domain.model.Registro;
import br.rn.sesed.sides.domain.model.Usuario;

@Component
public class RegistroMultipartJsonConvert {

	@Autowired
	private ModelMapper modelMapper;
	
	public Registro toDomainObject(RegistroMultiPartJson registroJson ) {
		return modelMapper.map(registroJson, Registro.class);
	}
	
	public void copyToDomainObject(RegistroMultiPartJson registroJson, Registro registro) {
		modelMapper.map(registroJson, registro);
	}
	
}
