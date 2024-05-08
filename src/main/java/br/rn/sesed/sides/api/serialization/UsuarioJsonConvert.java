package br.rn.sesed.sides.api.serialization;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.rn.sesed.sides.api.model.json.UsuarioJson;
import br.rn.sesed.sides.domain.model.Usuario;

@Component
public class UsuarioJsonConvert {

	@Autowired
	private ModelMapper modelMapper;
	
	public Usuario toDomainObject(UsuarioJson usuarioJson ) {
		return modelMapper.map(usuarioJson, Usuario.class);
	}
	
	public void copyToDomainObject(UsuarioJson usuarioJson, Usuario usuario) {
		modelMapper.map(usuarioJson, usuario);
	}
	
}
