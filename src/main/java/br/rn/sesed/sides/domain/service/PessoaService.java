package br.rn.sesed.sides.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.rn.sesed.sides.domain.exception.ErroAoSalvarUsuarioException;
import br.rn.sesed.sides.domain.model.Pessoa;
import br.rn.sesed.sides.domain.repository.PessoaRepository;

@Service
public class PessoaService {

	@Autowired
	private PessoaRepository pessoaRepository;

	public Pessoa salvar(Pessoa pessoa) {
		try {
			return pessoaRepository.save(pessoa);
		} catch (Exception e) {
			throw new ErroAoSalvarUsuarioException(e.getMessage());
		}
	}

}
