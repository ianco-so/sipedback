package br.rn.sesed.sides.api.serialization;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.rn.sesed.sides.api.model.dto.UsuarioDto;
import br.rn.sesed.sides.domain.desaparecidos.model.Usuario;

@Component
public class UsuarioDtoConvert {

	@Autowired
	private ModelMapper modelMapper;
	
	
	public UsuarioDto toDto(Usuario usuario) {
		return modelMapper.map(usuario, UsuarioDto.class);
	}
	
	public List<UsuarioDto> toCollectionModel(List<Usuario> usuarios) {
		return usuarios.stream()
				.map(usuario -> toDto(usuario))
				.collect(Collectors.toList());
	}
	
}
