package br.rn.sesed.sides.api.controller;


import java.io.IOException;
import java.util.Arrays;
import java.util.List;

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
import br.rn.sesed.sides.api.serialization.RegistroJsonConvert;
import br.rn.sesed.sides.core.ftp.FTPProperties;
import br.rn.sesed.sides.domain.exception.EntidadeNaoEncontradaException;
import br.rn.sesed.sides.domain.exception.ErroAoSalvarUsuarioException;
import br.rn.sesed.sides.domain.model.Pessoa;
import br.rn.sesed.sides.domain.model.Registro;
import br.rn.sesed.sides.domain.service.FTPService;
import br.rn.sesed.sides.domain.service.PessoaService;
import br.rn.sesed.sides.domain.service.RegistroService;
import br.rn.sesed.sides.domain.service.UsuarioService;
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
	private RegistroJsonConvert registroJsonConvert;

	@Autowired
	private RegistroDtoConvert registroDtoConvert;
	
	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private RegistroService registroService;
	
	@Autowired
	private FTPService ftpService;
	
	@Autowired
	private FTPProperties properties;
		
	@PostMapping(path = "/novo")
	@ResponseStatus(code = HttpStatus.CREATED)
	public void testFtp(@RequestParam(name = "fotos") MultipartFile[] files, @RequestPart(name = "registro") String registro)throws Exception {
		
		log.info("Map de Fotos (SIZE) ------> {}", files.length);
		Arrays.stream(files).forEach(
				t -> {
					try {
					
						log.info("Content Type -> {}", t.getContentType());
						if(t.getContentType().contains("image")) {
						ftpService.uploadFile(properties.getRemoteFilePath() + t.getOriginalFilename() , t.getInputStream());
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				);
		
//			fotos.forEach((str, foto) -> {
//					try {
//						ftpService.uploadFile(properties.getRemoteFilePath() + foto.getOriginalFilename() , foto.getInputStream());
//					}  catch (Exception e) {
//						throw new ErroAoSalvarFtpException(e.getMessage());
//					}				
//			});
	}

//	@ResponseStatus(HttpStatus.CREATED)
//	@RequestMapping(consumes = { MediaType.MULTIPART_FORM_DATA }, path = "/novo", method = RequestMethod.POST)
//	public @ResponseBody void adicionar(@ModelAttribute RegistroMultiPartJson registroJson) {
//		try {
//			
//				Usuario usuario = usuarioService.localizarUsuarioPorCpf(registroJson.getRegistro().cpfUsuario);
//	
//				Pessoa pessoa = pessoaJsonConvert.toDomainObject(registroJson.getRegistro().pessoa);
//	
//				Registro registro = registroJsonConvert.toDomainObject(registroJson.getRegistro());
//	
//				registro.setUsuario(usuario);
//				registro.setPessoas(new ArrayList<>());
//				registro.getPessoas().add(pessoa);
//	
//				registroService.salvar(registro);
//	
//			} catch (Exception e) {
//				throw new ErroAoSalvarRegistroException(e.getMessage());
//		}
//	}

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
			pessoaService.vincularRegistroPessoa(vincularDto.getIdp(),vincularDto.getIdr());
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
