package br.rn.sesed.sides.domain.service;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import javax.transaction.Transactional;
import javax.xml.bind.DatatypeConverter;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.rn.sesed.sides.api.model.dto.RegistroDto;
import br.rn.sesed.sides.api.model.json.RegistroJson;
import br.rn.sesed.sides.api.serialization.PessoaJsonConvert;
import br.rn.sesed.sides.api.serialization.RegistroDtoConvert;
import br.rn.sesed.sides.api.serialization.RegistroJsonConvert;
import br.rn.sesed.sides.domain.exception.EntidadeNaoEncontradaException;
import br.rn.sesed.sides.domain.exception.ErroAoSalvarEntidadeException;
import br.rn.sesed.sides.domain.exception.ErroAoSalvarRegistroException;
import br.rn.sesed.sides.domain.model.Pessoa;
import br.rn.sesed.sides.domain.model.Registro;
import br.rn.sesed.sides.domain.model.Usuario;
import br.rn.sesed.sides.domain.repository.PessoaRepository;
import br.rn.sesed.sides.domain.repository.RegistroRepository;

@Service
public class RegistroService {

	@Autowired
	private PessoaRepository pessoaRepository;

	@Autowired
	private RegistroRepository registroRepository;

	@Autowired
	private PessoaService pessoaService;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private PessoaJsonConvert pessoaJsonConvert;

	@Autowired
	private FTPService ftpService;

	@Autowired
	private RegistroJsonConvert registroJsonConvert;
	
	@Autowired
	private RegistroDtoConvert registroDtoConvert;

	@Transactional
	public Registro salvar(MultipartFile[] files, String registro) throws IOException {
		try {
			ftpService.status();

			RegistroJson registroJson = registroJsonConvert.toJsonObject(registro);

			Usuario usuario = usuarioService.localizarUsuarioPorCpf(registroJson.getCpfUsuario());

			Pessoa pessoa = pessoaJsonConvert.toDomainObject(registroJson.getPessoa());

			Registro registroDesaparecido = registroJsonConvert.toDomainObject(registroJson);

			registroDesaparecido.setUsuario(usuario);
			registroDesaparecido.setPessoas(new ArrayList<>());
			registroDesaparecido.getPessoas().add(pessoa);

			Registro registroSalvo = this.salvar(registroDesaparecido);
			Pessoa pessoaSalva = registroSalvo.getPessoas().get(0);

			Arrays.stream(files).forEach(t -> {
				try {
					if (t.getContentType().contains("image")) {
						if (!ftpService.existDir(String.valueOf(pessoaSalva.getId()))) {
							ftpService.createDir(String.valueOf(pessoaSalva.getId()));
						}
						ftpService.uploadFile(String.valueOf(pessoaSalva.getId()) + "/" + String.valueOf(pessoaSalva.getId()) + "_" + t.getOriginalFilename(),
								t.getInputStream());
					}
				} catch (IOException e) {
					throw new ErroAoSalvarRegistroException(e.getMessage());
				}
			});

			return registroSalvo;
		} catch (Exception e) {
			throw e;
		}
	}

	@Transactional
	public Registro salvar(RegistroJson registroJson) throws IOException {
		try {
			ftpService.status();

			
			Usuario usuario = usuarioService.localizarUsuarioPorCpf(registroJson.getCpfUsuario());

			Pessoa pessoa = pessoaJsonConvert.toDomainObject(registroJson.getPessoa());

			Registro registroDesaparecido = registroJsonConvert.toDomainObject(registroJson);

			registroDesaparecido.setUsuario(usuario);
			registroDesaparecido.setPessoas(new ArrayList<>());
			registroDesaparecido.getPessoas().add(pessoa);

			Registro registroSalvo = this.salvar(registroDesaparecido);
			Pessoa pessoaSalva = registroSalvo.getPessoas().get(0);

			byte[] imageFotoPrincipal;
			byte[] imageSegundaFoto;
			byte[] imageTerceiraFoto;
			if(pessoa.getFotoPrincipal().getContent().startsWith("data")){
				var content = pessoa.getFotoPrincipal().getContent();
				String imageDataBytes = content.substring(content.indexOf(",")+1);
				imageFotoPrincipal = DatatypeConverter.parseBase64Binary(imageDataBytes);
				InputStream isFoto = new ByteArrayInputStream(imageFotoPrincipal);
				if (!ftpService.existDir(String.valueOf(pessoaSalva.getId()))) {
					ftpService.createDir(String.valueOf(pessoaSalva.getId()));
				}
				ftpService.uploadFile(String.valueOf(pessoaSalva.getId()) + "/" + String.valueOf(pessoaSalva.getId()) + "_" + pessoa.getFotoPrincipal().getFileName(),
				isFoto);
			}

			if(pessoa.getSegundaFoto().getContent().startsWith("data")){
				var content = pessoa.getSegundaFoto().getContent();
				String imageDataBytes = content.substring(content.indexOf(",")+1);
				imageSegundaFoto = DatatypeConverter.parseBase64Binary(imageDataBytes);
				InputStream isFoto = new ByteArrayInputStream(imageSegundaFoto);
				if (!ftpService.existDir(String.valueOf(pessoaSalva.getId()))) {
					ftpService.createDir(String.valueOf(pessoaSalva.getId()));
				}
				ftpService.uploadFile(String.valueOf(pessoaSalva.getId()) + "/" + String.valueOf(pessoaSalva.getId()) + "_" + pessoa.getSegundaFoto().getFileName(),
				isFoto);
			}
			
			if(pessoa.getTerceiraFoto().getContent().startsWith("data")){
				var content = pessoa.getTerceiraFoto().getContent();
				String imageDataBytes = content.substring(content.indexOf(",")+1);
				imageTerceiraFoto = DatatypeConverter.parseBase64Binary(imageDataBytes);
				InputStream isFoto = new ByteArrayInputStream(imageTerceiraFoto);
				if (!ftpService.existDir(String.valueOf(pessoaSalva.getId()))) {
					ftpService.createDir(String.valueOf(pessoaSalva.getId()));
				}
				ftpService.uploadFile(String.valueOf(pessoaSalva.getId()) + "/" + String.valueOf(pessoaSalva.getId()) + "_" + pessoa.getTerceiraFoto().getFileName(),
				isFoto);
			}

			// Arrays.stream(files).forEach(t -> {
			// 	try {
			// 		if (t.getContentType().contains("image")) {
			// 			if (!ftpService.existDir(String.valueOf(pessoaSalva.getId()))) {
			// 				ftpService.createDir(String.valueOf(pessoaSalva.getId()));
			// 			}
			// 			ftpService.uploadFile(String.valueOf(pessoaSalva.getId()) + "/" + String.valueOf(pessoaSalva.getId()) + "_" + t.getOriginalFilename(),
			// 					t.getInputStream());
			// 		}
			// 	} catch (IOException e) {
			// 		throw new ErroAoSalvarRegistroException(e.getMessage());
			// 	}
			// });

			return registroSalvo;
		} catch (Exception e) {
			throw e;
		}
	}

	@Transactional
	public Registro salvar(Registro registro) {
		try {
			Pessoa pessoa = pessoaService.salvar(registro.getPessoas().get(0));
			registro.getPessoas().clear();
			registro.getPessoas().add(pessoa);
			return registroRepository.save(registro);
		} catch (Exception e) {
			throw new ErroAoSalvarEntidadeException(e.getMessage());
		}
	}
	
	@Transactional
	public RegistroDto salvar(MultipartFile[] files) {
		try {
			Registro reg = new Registro();
			List<Pessoa> pessoa = new ArrayList<>();
			reg.setPessoas(pessoa);
			Registro pessoaSalva = this.salvar(reg);	
			Arrays.stream(files).forEach(t -> {
				try {
					if (t.getContentType().contains("image")) {
						if (!ftpService.existDir(String.valueOf(pessoaSalva.getId()))) {
							ftpService.createDir(String.valueOf(pessoaSalva.getId()));
						}
						ftpService.uploadFile(String.valueOf(pessoaSalva.getId()) + "/" + String.valueOf(pessoaSalva.getId()) + "_" + t.getOriginalFilename(),
								t.getInputStream());
					}
				} catch (IOException e) {
					throw new ErroAoSalvarRegistroException(e.getMessage());
				}
			});	
			RegistroDto dto = registroDtoConvert.toDto(pessoaSalva);
			return dto;
		} catch (Exception e) {
			throw new ErroAoSalvarEntidadeException(e.getMessage());
		}
	}

	public List<Registro> localizarPorCpf(String cpf) {

		try {
			return registroRepository.findByCpfComunicante(cpf);
		} catch (Exception e) {
			throw new EntidadeNaoEncontradaException(e.getMessage());
		}
	}

	public List<Registro> localizarSimplePorCpf(String cpf) {
		try {
			return registroRepository.findBySimpleCpfComunicante(cpf);
		} catch (Exception e) {
			throw new EntidadeNaoEncontradaException(e.getMessage());
		}
	}

	public Registro localizarPorId(Long id) {

		try {
			ftpService.listDir("/");
			Registro reg = registroRepository.findById(id).get();
			for(Pessoa pessoa: reg.getPessoas()) {
				pessoa.setFotos(ftpService.getFotosFromRegistroId("/"+pessoa.getId()+"/"));
			}
			return reg;
		} catch (Exception e) {
			throw new EntidadeNaoEncontradaException(e.getMessage());
		}
	}

	public List<Registro> findByPessoa(Pessoa pessoa) {
		try {
			return pessoaRepository.findByNome(pessoa.getNome()).getRegistros();
		} catch (Exception e) {
			throw new EntidadeNaoEncontradaException(e.getMessage());
		}
	}

	public List<Registro> findAll() {
		try {
			return registroRepository.findAll();
		} catch (Exception e) {
			throw new EntidadeNaoEncontradaException(e.getMessage());
		}
	}



}
