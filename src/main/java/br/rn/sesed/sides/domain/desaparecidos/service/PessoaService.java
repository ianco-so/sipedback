package br.rn.sesed.sides.domain.desaparecidos.service;

import java.util.NoSuchElementException;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.rn.sesed.sides.api.model.json.PessoaJson;
import br.rn.sesed.sides.api.serialization.PessoaJsonConvert;
import br.rn.sesed.sides.domain.desaparecidos.model.Pessoa;
import br.rn.sesed.sides.domain.desaparecidos.model.Registro;
import br.rn.sesed.sides.domain.desaparecidos.model.RegistroVinculado;
import br.rn.sesed.sides.domain.desaparecidos.repository.PessoaRepository;
import br.rn.sesed.sides.domain.desaparecidos.repository.RegistroRepository;
import br.rn.sesed.sides.domain.desaparecidos.repository.RegistroVinculadoRepository;
import br.rn.sesed.sides.domain.exception.ErroAoSalvarUsuarioException;
import br.rn.sesed.sides.domain.exception.PessoaNaoEncontradaException;
import br.rn.sesed.sides.domain.exception.RegistroNaoEncontradoException;

@Service
public class PessoaService {
	
	@Autowired
	private RegistroRepository registroRepository;

	@Autowired
	private RegistroVinculadoRepository registroVinculadoRepository;

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


	@Transactional
	public void vincularRegistroBoletim(Long idRegistroBoletim, Long idRegistroInstituicao) throws Exception {
		try {
			
			Registro registroBoletim = registroRepository.findById(idRegistroBoletim).orElseThrow(() -> new RegistroNaoEncontradoException(idRegistroBoletim));
			
			Registro registroInstituicao = registroRepository.findById(idRegistroInstituicao).orElseThrow(() -> new RegistroNaoEncontradoException(idRegistroInstituicao));
			
			var registroVinculado = new RegistroVinculado();
			registroVinculado.setRegistroBoletim(registroBoletim);
			registroVinculado.setRegistroInstituicao(registroInstituicao);

			registroVinculadoRepository.save(registroVinculado);

			registroBoletim.setVinculado(true);
			registroInstituicao.setVinculado(true);
			registroRepository.save(registroBoletim);
			registroRepository.save(registroInstituicao);

			
		} catch (IllegalArgumentException ex) {
			throw new Exception("ID do Registro ou Boletim não pode ser vazio ou nulo");
		} catch(NoSuchElementException e) {
			throw new RegistroNaoEncontradoException(idRegistroBoletim);
		}catch(DataIntegrityViolationException e){
			throw new DataIntegrityViolationException("Boletins já vincluados anteriormente");
		}catch(Exception e) {
			throw e;
		}
	}


}
