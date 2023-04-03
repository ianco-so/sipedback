package br.rn.sesed.sides.domain.service;

import java.util.NoSuchElementException;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.rn.sesed.sides.api.model.json.PessoaJson;
import br.rn.sesed.sides.api.serialization.PessoaJsonConvert;
import br.rn.sesed.sides.domain.exception.ErroAoSalvarUsuarioException;
import br.rn.sesed.sides.domain.exception.PessoaNaoEncontradaException;
import br.rn.sesed.sides.domain.exception.RegistroNaoEncontradoException;
import br.rn.sesed.sides.domain.model.Pessoa;
import br.rn.sesed.sides.domain.model.Registro;
import br.rn.sesed.sides.domain.repository.PessoaRepository;
import br.rn.sesed.sides.domain.repository.RegistroRepository;

@Service
public class PessoaService {
	
	@Autowired
	private RegistroRepository registroRepository;

	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private PessoaJsonConvert pessoaConverter;

	public Pessoa salvar(Pessoa pessoa) {
		try {
			return pessoaRepository.save(pessoa);
		} catch (Exception e) {
			throw new ErroAoSalvarUsuarioException(e.getMessage());
		}
	}
	
	@Transactional
	public PessoaJson vincularRegistroPessoa(Long idPessoa, Long idRegistro) throws Exception {
		try {
			
			Pessoa pessoa = pessoaRepository.findById(idPessoa).orElseThrow(() -> new PessoaNaoEncontradaException(idPessoa));
			
			Registro registro = registroRepository.findById(idRegistro).orElseThrow(() -> new RegistroNaoEncontradoException(idRegistro));
			pessoa.getRegistros().add(registro);
			
			Pessoa pessoaResult = pessoaRepository.save(pessoa);
			PessoaJson pessoaJson = pessoaConverter.toPessoaJson(pessoaResult);
			return pessoaJson;
		} catch (IllegalArgumentException ex) {
			throw new Exception("ID da pessoa não pode ser vazio ou nulo");
		} catch(NoSuchElementException e) {
			throw new PessoaNaoEncontradaException(idPessoa);
		}catch(Exception e) {
			throw e;
		}
	}

}
