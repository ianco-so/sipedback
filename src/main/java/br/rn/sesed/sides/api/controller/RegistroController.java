package br.rn.sesed.sides.api.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

import com.fasterxml.jackson.databind.ObjectMapper;

import br.rn.sesed.sides.api.model.dto.RegistroDto;
import br.rn.sesed.sides.api.model.dto.RegistroSimpleDto;
import br.rn.sesed.sides.api.model.dto.RegistroVinculadoDto;
import br.rn.sesed.sides.api.model.dto.VincularDto;
import br.rn.sesed.sides.api.model.dto.VincularRegistroDto;
import br.rn.sesed.sides.api.model.json.ImageJson;
import br.rn.sesed.sides.api.model.json.PessoaJson;
import br.rn.sesed.sides.api.model.json.RegistroJson;
import br.rn.sesed.sides.api.model.json.RegistroTypeJson;
import br.rn.sesed.sides.api.serialization.PessoaJsonConvert;
import br.rn.sesed.sides.api.serialization.RegistroDtoConvert;
import br.rn.sesed.sides.api.serialization.RegistroVinculadoDtoConvert;
import br.rn.sesed.sides.core.modelmapper.ModelMapperConverter;
import br.rn.sesed.sides.domain.desaparecidos.model.Pessoa;
import br.rn.sesed.sides.domain.desaparecidos.model.Registro;
import br.rn.sesed.sides.domain.desaparecidos.service.FTPService;
import br.rn.sesed.sides.domain.desaparecidos.service.PessoaService;
import br.rn.sesed.sides.domain.desaparecidos.service.RegistroService;
import br.rn.sesed.sides.domain.exception.EntidadeNaoEncontradaException;
import br.rn.sesed.sides.domain.exception.ErroAoConectarFtpException;
import br.rn.sesed.sides.domain.exception.ErroAoSalvarUsuarioException;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@RequestMapping("/registro")
public class RegistroController {
	
	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	BuildProperties buildProperties;
	
	@Autowired
	private PessoaService pessoaService;

	@Autowired
	private PessoaJsonConvert pessoaJsonConvert;

	@Autowired
	private RegistroVinculadoDtoConvert registroVinculadoDtoConvert;

	@Autowired
	private RegistroDtoConvert registroDtoConvert;

	@Autowired
	private RegistroService registroService;

	@Autowired
	private ModelMapperConverter mapperConverter;

	@Autowired
    private FTPService ftpService;

	@PostMapping(path = "/novo")
	@ResponseStatus(code = HttpStatus.CREATED)
	public void adicionar(	@RequestParam(name = "fotos", required = false) MultipartFile[] files,	
							@RequestPart(name = "registro", required = false) String registro,
							HttpServletRequest request) throws Exception {
		try {

			log.info("Recebendo Json... {}", registro);
			// registro = registro.replace("T00:00:00.000Z", "");

			// registro = "{\"bairro\":\"\",\"boletim\":\"\",\"cep\":\"\",\"complemento\":\"\",\"cpfComunicante\":\"\",\"cpfUsuario\":\"06760369459\",\"dataBoletim\":\"\",\"dataDesaparecimento\":\"\",\"delegacia\":\"\",\"detalhes\":\"\",\"emailComunicante\":\"\",\"lat\":\"\",\"logradouro\":\"\",\"lon\":\"\",\"municipio\":\"\",\"nomeComunicante\":\"\",\"nomeSocialComunicante\":\"\",\"numero\":\"\",\"pessoa\":{\"acessorios\":\"\",\"altura\":\"\",\"bairro\":\"\",\"biotipo\":\"\",\"boAparelhoAuditivo\":false,\"boAuditiva\":false,\"boCadeirante\":false,\"boComorbidade\":false,\"boDeficiente\":false,\"boDividasAgiotas\":false,\"boDividasNarcotraficantes\":false,\"boFisica\":false,\"boHistoricoDesaparecimento\":false,\"boInstituicaoEnsino\":false,\"boIntelectual\":false,\"boInteracaosocial\":false,\"boLibras\":false,\"boMemoria\":false,\"boMuleta\":false,\"boNeuroDesenvolvimento\":false,\"boNomeSocial\":false,\"boSenil\":false,\"boVisual\":false,\"cabelo\":\"\",\"celular\":\"\",\"comorbidade\":\"\",\"complemento\":\"\",\"corCabelo\":\"\",\"corOlhos\":\"\",\"corPele\":\"\",\"corteCabelo\":\"\",\"cpf\":\"\",\"dataNascimento\":\"1212-12-12\",\"drogas\":\"\",\"escolaridade\":\"\",\"estadoPsiquico\":\"\",\"fisica\":\"\",\"idadeAproximada\":12,\"identidadeGenero\":\"homem\",\"instituicaoEnsino\":\"\",\"intelectual\":\"\",\"interacaosocial\":\"\",\"logradouro\":\"\",\"mae\":\"\",\"marcas\":\"\",\"medicacao\":\"\",\"memoria\":\"\",\"municipio\":\"\",\"nacionalidade\":\"\",\"naturalidade\":\"\",\"neuroDesenvolvimento\":\"\",\"nome\":\"\",\"nomeSocial\":\"Teste - William \",\"numero\":\"\",\"orgaoEmissorRg\":\"\",\"pai\":\"\",\"rg\":\"\",\"situacaoEconomica\":\"\",\"tipoSanguineo\":\"a+\",\"uf\":\"\",\"ufNaturalidade\":\"\",\"ufrg\":\"\"},\"pontoReferencia\":\"\",\"relacacoVitima\":\"\",\"rgComunicante\":\"\",\"rgOrgaoEmissorComunicante\":\"\",\"telefoneComunicante\":\"\",\"tipoRegistro\":2,\"uf\":\"\",\"ufRgComunicante\":\"\"}";

			log.info("Registrando... {}", registro);
			registroService.salvar(files,registro);						
		} catch (ErroAoConectarFtpException e) {
			throw new ErroAoConectarFtpException(e.getMessage());
		} catch (Exception e) {
			log.error("Erro salvando registro: ", e.getCause());
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
			e.printStackTrace();
			throw new EntidadeNaoEncontradaException(e.getMessage());
		}
	}

	@GetMapping("/boletim-vinculado/{id}")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody RegistroVinculadoDto localizarRegistroVinculado(@PathVariable("id") Long id) {
		try {

			var registroBoletim = registroService.localizarPorId(id);

			var registroVinculado = registroService.findRegistroVinculadoByBoletim(id);

			registroVinculado.setRegistroBoletim(registroBoletim);
			
			RegistroVinculadoDto rvdto = registroVinculadoDtoConvert.toDto(registroVinculado);
			return rvdto;

		} catch (Exception e) {
			e.printStackTrace();
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


	@PostMapping("/images")
	@ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Resource> getImage(@RequestBody ImageJson imageJson) {

		String nomeFoto = String.valueOf(imageJson.getId()).concat("_".concat(imageJson.getImageName()));
		try {
			
			byte[] imageData = ftpService.downloadImageWithoutExtension(String.valueOf(imageJson.getId()), nomeFoto);
			
			ByteArrayResource resource = new ByteArrayResource(imageData);
			HttpHeaders headers = new HttpHeaders();
			headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=" + nomeFoto);
	
			return ResponseEntity.ok()
					.headers(headers)
					.contentLength(imageData.length)
					.contentType(MediaType.IMAGE_JPEG)
					.body(resource);

		} catch (IOException e) {
			//return ResponseEntity.notFound().build();
			return ResponseEntity.ok()
					.contentLength(0)
					.contentType(MediaType.APPLICATION_JSON)
					.body(new ByteArrayResource(new byte[0]));
		}
		
    }

	@GetMapping("/images")
	public ResponseEntity<Resource> getImage(@RequestParam(name = "imageId") String imageId, @RequestParam(name = "imageName") String imageName) throws IOException {

		String nomeFoto = String.valueOf(imageId.concat("_".concat(imageName)));
		try {
			
			byte[] imageData = ftpService.downloadImageWithoutExtension(String.valueOf(imageId), nomeFoto);
			
			ByteArrayResource resource = new ByteArrayResource(imageData);
			HttpHeaders headers = new HttpHeaders();
			headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=" + nomeFoto);
	
			return ResponseEntity.ok()
					.headers(headers)
					.contentLength(imageData.length)
					.contentType(MediaType.IMAGE_JPEG)
					.body(resource);

		} catch (IOException e) {
			//return ResponseEntity.notFound().build();
			return ResponseEntity.ok()
					.contentLength(0)
					.contentType(MediaType.APPLICATION_JSON)
					.body(new ByteArrayResource(new byte[0]));
		}
		
	}



}
