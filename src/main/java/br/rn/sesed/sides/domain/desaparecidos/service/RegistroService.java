package br.rn.sesed.sides.domain.desaparecidos.service;

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
import br.rn.sesed.sides.api.model.json.RegistroTypeJson;
import br.rn.sesed.sides.api.serialization.PessoaJsonConvert;
import br.rn.sesed.sides.api.serialization.RegistroDtoConvert;
import br.rn.sesed.sides.api.serialization.RegistroJsonConvert;
import br.rn.sesed.sides.domain.desaparecidos.model.Pessoa;
import br.rn.sesed.sides.domain.desaparecidos.model.Registro;
import br.rn.sesed.sides.domain.desaparecidos.model.RegistroVinculado;
import br.rn.sesed.sides.domain.desaparecidos.model.Usuario;
import br.rn.sesed.sides.domain.desaparecidos.repository.PessoaRepository;
import br.rn.sesed.sides.domain.desaparecidos.repository.RegistroRepository;
import br.rn.sesed.sides.domain.desaparecidos.repository.RegistroVinculadoRepository;
import br.rn.sesed.sides.domain.exception.EntidadeNaoEncontradaException;
import br.rn.sesed.sides.domain.exception.ErroAoSalvarEntidadeException;
import br.rn.sesed.sides.domain.exception.ErroAoSalvarRegistroException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RegistroService {

	private InputStream isFotoPrincipal = null;
	private InputStream isSegundaFoto = null;
	private InputStream isTerceiraFoto = null;

	@Autowired
	private PessoaRepository pessoaRepository;

	@Autowired
	private RegistroRepository registroRepository;
	
	@Autowired
	private RegistroVinculadoRepository registroVinculadoRepository;
	
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
	public Registro salvar(MultipartFile[] files, String registro) throws Exception {
		try {
			ftpService.status();
			
			RegistroJson registroJson = registroJsonConvert.toJsonObject(registro);

			Usuario usuario = usuarioService.localizarUsuarioPorCpf(registroJson.getCpfUsuario());

			Pessoa pessoa = pessoaJsonConvert.toDomainObject(registroJson.getPessoa());

			if(pessoa.getNome() != null && pessoa.getNome().isEmpty()){
				if(pessoa.getNomeSocial() != null){
					pessoa.setNome(pessoa.getNomeSocial());
				}
			}
			if(files != null){
				this.serializaFotos(files, pessoa);
			}

			Registro registroDesaparecido = registroJsonConvert.toDomainObject(registroJson);

			registroDesaparecido.setUsuario(usuario);
			registroDesaparecido.setPessoas(new ArrayList<>());
			registroDesaparecido.getPessoas().add(pessoa);

			
			Registro registroSalvo = this.salvar(registroDesaparecido);
			Pessoa pessoaSalva = registroSalvo.getPessoas().get(0);

			if(files != null){
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
			}

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
				int idx = pessoa.getFotoPrincipal().getFileName().indexOf(".");
				String nomeArquivo = pessoa.getFotoPrincipal().getFileName().replace(pessoa.getFotoPrincipal().getFileName().substring(0, idx),"fotoprinpial");
				ftpService.uploadFile(String.valueOf(pessoaSalva.getId()) + "/" + String.valueOf(pessoaSalva.getId()) + "_" + nomeArquivo,
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

				int idx = pessoa.getSegundaFoto().getFileName().indexOf(".");
				String nomeArquivo = pessoa.getSegundaFoto().getFileName().replace(pessoa.getSegundaFoto().getFileName().substring(0, idx),"segundafoto");
				ftpService.uploadFile(String.valueOf(pessoaSalva.getId()) + "/" + String.valueOf(pessoaSalva.getId()) + "_" + nomeArquivo,
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
				
				int idx = pessoa.getTerceiraFoto().getFileName().indexOf(".");
				String nomeArquivo = pessoa.getTerceiraFoto().getFileName().replace(pessoa.getTerceiraFoto().getFileName().substring(0, idx),"terceirafoto");
				ftpService.uploadFile(String.valueOf(pessoaSalva.getId()) + "/" + String.valueOf(pessoaSalva.getId()) + "_" + nomeArquivo,
				isFoto);
			}

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
			e.printStackTrace();
			throw new ErroAoSalvarEntidadeException(e.getMessage());
		}
	}
	
	@Transactional
	public RegistroDto salvar(MultipartFile[] files) {
		try {
			Registro registro = new Registro();
			List<Pessoa> pessoas = new ArrayList<>();
			registro.setPessoas(pessoas);
			Registro registroSalvo = this.salvar(registro);	
			Arrays.stream(files).forEach(t -> {
				try {
					if (t.getContentType().contains("image")) {
						if (!ftpService.existDir(String.valueOf(registroSalvo.getId()))) {
							ftpService.createDir(String.valueOf(registroSalvo.getId()));
						}
						ftpService.uploadFile(String.valueOf(registroSalvo.getId()) + "/" + String.valueOf(registroSalvo.getId()) + "_" + t.getOriginalFilename(),
								t.getInputStream());
					}
				} catch (IOException e) {
					throw new ErroAoSalvarRegistroException(e.getMessage());
				}
			});	
			RegistroDto dto = registroDtoConvert.toDto(registroSalvo);
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
			Registro reg = registroRepository.findById(id).get();
			return reg;
		} catch (Exception e) {
			throw new EntidadeNaoEncontradaException(e.getMessage());
		}
	}

	public Registro localizarBoletimVinculadoPorId(Long id) {

		try {
			Registro reg = registroRepository.findBoletimVinculadoById(id).get();
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

			var registros = registroRepository.findAll();

			return registros;
		} catch (Exception e) {
			throw new EntidadeNaoEncontradaException(e.getMessage());
		}
	}

	public List<Registro> findAllPesquisa() {
		try {
			return registroRepository.findAllPesquisa();
		} catch (Exception e) {
			throw new EntidadeNaoEncontradaException(e.getMessage());
		}
	}

    public List<Registro> findAllRegistoNaoVinculado(RegistroTypeJson registroTypeJson) {
        try {


			var registros = registroRepository.findByTipoRegistroAndVinculado(2L, false);

			return registros;
		} catch (Exception e) {
			throw new EntidadeNaoEncontradaException(e.getMessage());
		}
    }

	public List<Registro> findAllRegistrosPorTipo(RegistroTypeJson registroTypeJson) {
        try {


			var registros = registroRepository.findByTipoRegistroAndVinculado(registroTypeJson.getTipo().getValue(), registroTypeJson.getVinculado());

			return registros;
		} catch (Exception e) {
			throw new EntidadeNaoEncontradaException(e.getMessage());
		}
    }

    public List<Registro> findAllBoletimNaoVinculado() {
        try {


			var registros = registroRepository.findByTipoRegistroAndVinculado(1L, false);

			return registros;
		} catch (Exception e) {
			throw new EntidadeNaoEncontradaException(e.getMessage());
		}
    }

	public RegistroVinculado findRegistroVinculadoByBoletim(Long idBoletim){
		try {

			
			var boletimVinculado = registroVinculadoRepository.findById(idBoletim);

			return boletimVinculado.get();
		} catch (Exception e) {
			throw new EntidadeNaoEncontradaException(e.getMessage());
		}



	}

	public void serializaFotos(MultipartFile[] files, Pessoa pessoa) throws Exception{

		try{
		
			try {

				for (MultipartFile file : files) {
					byte[] fileBytes = file.getBytes();
					String teste = "data:image/jpeg;base64,".concat(Base64.getEncoder().encodeToString(fileBytes));
					if(file.getOriginalFilename() != null && file.getOriginalFilename().contains("fotoprincipal")){
						pessoa.setStFotoprincipal("data:image/jpeg;base64,".concat(Base64.getEncoder().encodeToString(fileBytes)));
					}else if(file.getOriginalFilename() != null && file.getOriginalFilename().contains("segundafoto")){
						pessoa.setStSegundafoto("data:image/jpeg;base64,".concat(Base64.getEncoder().encodeToString(fileBytes)));
					}else if(file.getOriginalFilename() != null && file.getOriginalFilename().contains("terceirafoto")){
						pessoa.setStTerceirafoto("data:image/jpeg;base64,".concat(Base64.getEncoder().encodeToString(fileBytes)));
					}
										
				}
				
			} catch (IOException e) {
				throw new ErroAoSalvarRegistroException(e.getMessage());
			}
		


			

	}catch (Exception e){
		throw e;
	}
	}

	


}
