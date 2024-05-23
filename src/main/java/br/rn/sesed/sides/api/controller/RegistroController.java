package br.rn.sesed.sides.api.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import br.rn.sesed.sides.api.model.dto.VincularRegistroDto;
import br.rn.sesed.sides.api.model.json.PessoaJson;
import br.rn.sesed.sides.api.model.json.RegistroJson;
import br.rn.sesed.sides.api.model.json.RegistroTypeJson;
import br.rn.sesed.sides.api.serialization.PessoaJsonConvert;
import br.rn.sesed.sides.api.serialization.RegistroDtoConvert;
import br.rn.sesed.sides.core.modelmapper.ModelMapperConverter;
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
	
	@Autowired
	BuildProperties buildProperties;
	
	@Autowired
	private PessoaService pessoaService;

	@Autowired
	private PessoaJsonConvert pessoaJsonConvert;

	@Autowired
	private RegistroDtoConvert registroDtoConvert;

	@Autowired
	private RegistroService registroService;

	@Autowired
	private ModelMapperConverter mapperConverter;

	@PostMapping(path = "/novo")
	@ResponseStatus(code = HttpStatus.CREATED)
	public void adicionar(	@RequestParam(name = "fotos", required = false) MultipartFile[] files,	
							@RequestPart(name = "registro", required = false) String registro,
							HttpServletRequest request) throws Exception {
		try {

			
			registro = registro.replace("T00:00:00.000Z", "");


			log.info("Registrando... {}", registro);
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

	@PostMapping("/boletim")
	@ResponseStatus(HttpStatus.CREATED)
	public @ResponseBody RegistroDto postNovoRegistro(@RequestBody RegistroJson registroJson) throws Exception {
		try {
				
			Registro temp = registroService.salvar(registroJson);
			RegistroDto dto = registroDtoConvert.toDto(temp);
			return dto;

		}catch (org.springframework.security.core.AuthenticationException e) {
 			e.printStackTrace();
	   		System.out.println("dando ruim aqui");
			return null;
		} catch (ErroAoConectarFtpException e) {
			throw new ErroAoConectarFtpException(e.getMessage());
		} catch (Exception e) {
			throw e;
		}
	
	}
	@PostMapping("/version")
	public BuildProperties version(@RequestBody String registroJson) {
		return buildProperties;
	}	

	@PostMapping(path = "/listar")
	public List<RegistroDto> getListaRegistro() throws Exception {
		try {
			
			List<Registro> registros = registroService.findAllBoletimNaoVinculado();

			List<RegistroDto> registroDtos = mapperConverter.mapToList(registros, RegistroDto.class);

			return registroDtos;
		} catch (ErroAoConectarFtpException e) {
			throw new ErroAoConectarFtpException(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@PostMapping(path = "/listar/tipo")
	public List<RegistroDto> getListaRegistros(@RequestBody RegistroTypeJson registroTypeJson) throws Exception {
		try {
			
			List<Registro> registros = registroService.findAllRegistrosPorTipo(registroTypeJson);

			List<RegistroDto> registroDtos = mapperConverter.mapToList(registros, RegistroDto.class);

			return registroDtos;
		} catch (ErroAoConectarFtpException e) {
			throw new ErroAoConectarFtpException(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@PostMapping(path = "/instituicao/listar")
	public List<RegistroDto> getListaRegistroInsituicoes(@RequestBody RegistroTypeJson registroTypeJson) throws Exception {
		try {
			
			List<Registro> registros = registroService.findAllRegistoNaoVinculado(registroTypeJson);

			List<RegistroDto> registroDtos = mapperConverter.mapToList(registros, RegistroDto.class);

			return registroDtos;
		} catch (ErroAoConectarFtpException e) {
			throw new ErroAoConectarFtpException(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
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
	public void vincularPessoa(@RequestBody VincularDto vincularDto) throws Exception {
		try {
			pessoaService.vincularRegistroPessoa(vincularDto.getIdpessoa(), vincularDto.getIdregistro());
		} catch (Exception e) {
			throw e;
		}
	}

	@PostMapping("/vincular/boletim")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<?> vincularBoletim(@RequestBody VincularRegistroDto vincularRegistroDto, HttpServletResponse response) throws Exception {
		try {
			pessoaService.vincularRegistroBoletim(vincularRegistroDto.getIdregistroBoletim(), vincularRegistroDto.getIdregistroInstituicao());
			return new ResponseEntity<>(HttpStatus.OK);
		
		}catch(DataIntegrityViolationException e){
			return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
		}catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
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
