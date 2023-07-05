package br.rn.sesed.sides.api.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.rn.sesed.sides.api.model.dto.RegistroDto;
import br.rn.sesed.sides.api.model.dto.RegistroSimpleDto;
import br.rn.sesed.sides.api.model.dto.VincularDto;
import br.rn.sesed.sides.api.model.json.PessoaJson;
import br.rn.sesed.sides.api.serialization.PessoaJsonConvert;
import br.rn.sesed.sides.api.serialization.RegistroDtoConvert;
import br.rn.sesed.sides.domain.exception.EntidadeNaoEncontradaException;
import br.rn.sesed.sides.domain.exception.ErroAoConectarFtpException;
import br.rn.sesed.sides.domain.exception.ErroAoSalvarUsuarioException;
import br.rn.sesed.sides.domain.model.Pessoa;
import br.rn.sesed.sides.domain.model.Registro;
import br.rn.sesed.sides.domain.service.PessoaService;
import br.rn.sesed.sides.domain.service.RegistroService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/registro")
public class RegistroController {

	private static final Logger logger = LoggerFactory.getLogger(RegistroController.class);

	@Autowired
	private PessoaService pessoaService;

	@Autowired
	private PessoaJsonConvert pessoaJsonConvert;

	@Autowired
	private RegistroDtoConvert registroDtoConvert;

	@Autowired
	private RegistroService registroService;

	@PostMapping(path = "/novo")
	@ResponseStatus(code = HttpStatus.CREATED)
	public void adicionar(	@RequestParam(name = "fotos", required = false) MultipartFile[] files,	
							@RequestPart(name = "registro", required = false) String registro,
							HttpServletRequest request) throws Exception {
		try {
			registroService.salvar(files,registro);						
		} catch (ErroAoConectarFtpException e) {
			throw new ErroAoConectarFtpException(e.getMessage());
		} catch (Exception e) {
			throw e;
		}
	}
	
	@GetMapping(path = "/novo/rotafx")
	public @ResponseBody RegistroDto getNovoRegistro() throws Exception {
		try {
			Registro registro = new Registro();
			Pessoa pessoa = new Pessoa();
			pessoa.setNome("");
			List<Pessoa> lista = new ArrayList<Pessoa>();
			lista.add(pessoa);
			registro.setPessoas(lista);			
			Registro temp = registroService.salvar(registro);
			RegistroDto dto = registroDtoConvert.toDto(temp);
			return dto;
		} catch (ErroAoConectarFtpException e) {
			throw new ErroAoConectarFtpException(e.getMessage());
		} catch (Exception e) {
			throw e;
		}
	}

	@GetMapping("/usuario/{cpf}")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody List<RegistroSimpleDto> localizarPorUsuario(@PathVariable("cpf") String cpf) {
		try {

			List<Registro> registros = registroService.localizarSimplePorCpf(cpf);

			return registroDtoConvert.toSimpleCollectionModel(registros);

		} catch (Exception e) {
			throw new EntidadeNaoEncontradaException(e.getMessage());
		}
	}

	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody RegistroDto localizarRegistro(@PathVariable("id") Long id) {
		try {

			Registro registro = registroService.localizarPorId(id);
			
			return registroDtoConvert.toDto(registro);

		} catch (Exception e) {
			throw new EntidadeNaoEncontradaException(e.getMessage());
		}
	}

	@PostMapping("/pessoa")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody List<RegistroDto> localizarRegistroPorPessoa(@RequestBody PessoaJson pessoaJson) {
		try {

			Pessoa pessoa = pessoaJsonConvert.toDomainObject(pessoaJson);

			List<Registro> registros = registroService.findByPessoa(pessoa);

			return registroDtoConvert.toCollectionModel(registros);
		} catch (Exception e) {
			throw new ErroAoSalvarUsuarioException(e.getMessage());
		}
	}

	@PostMapping("/vincular")
	@ResponseStatus(HttpStatus.OK)
	public void vincular(@RequestBody VincularDto vincularDto) throws Exception {
		try {
			pessoaService.vincularRegistroPessoa(vincularDto.getIdp(), vincularDto.getIdr());
		} catch (Exception e) {
			throw e;
		}
	}

//	@PostMapping("/pessoa")
//	@ResponseStatus(HttpStatus.OK)
//	public @ResponseBody List<RegistroDto> localizarRegistroporPessoa(@RequestBody PessoaJson pessoaJson) {
//		try {
//
//			List<Pessoa> pessoas = new ArrayList<>();
//			Pessoa pessoa = new Pessoa();
//			pessoa.setNome(pessoaJson.getNome());
//			pessoas.add(pessoa);
//			List<Registro> registros = registroService.localizarPorPessoas(pessoas);
//
//			return registroDtoConvert.toCollectionModel(registros);
//
//		} catch (Exception e) {
//			throw new EntidadeNaoEncontradaException(e.getMessage());
//		}
//	}

}
