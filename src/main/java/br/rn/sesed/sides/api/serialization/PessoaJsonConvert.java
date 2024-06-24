package br.rn.sesed.sides.api.serialization;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.rn.sesed.sides.api.model.json.PessoaJson;
import br.rn.sesed.sides.api.model.json.UsuarioJson;
import br.rn.sesed.sides.domain.desaparecidos.model.Pessoa;
import br.rn.sesed.sides.domain.desaparecidos.model.Usuario;

@Component
public class PessoaJsonConvert {

	@Autowired
	private ModelMapper modelMapper;
	
	public Pessoa toDomainObject(PessoaJson pessoaJson ) {
		return modelMapper.map(pessoaJson, Pessoa.class);
	}
	
	public void copyToDomainObject(UsuarioJson usuarioJson, Usuario usuario) {
		modelMapper.map(usuarioJson, usuario);
	}
	
	public PessoaJson toPessoaJson(Pessoa pessoa) {
		return modelMapper.map(pessoa, PessoaJson.class);
	}
	
}
