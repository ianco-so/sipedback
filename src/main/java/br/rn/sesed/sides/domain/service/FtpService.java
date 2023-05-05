package br.rn.sesed.sides.domain.service;

import java.io.InputStream;
import java.security.InvalidParameterException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.rn.sesed.sides.api.model.json.RegistroMultiPartJson;
import br.rn.sesed.sides.core.ftp.FtpClient;

@Service
public class FtpService {

	@Autowired
	private FtpClient ftpcliente;
	
	public void upload(String path, RegistroMultiPartJson json) throws Exception {
		try {
			sendToFtp(path, json.getFotoPrincipal().getInputStream());
		} catch (Exception e) {
			throw e;
		}
	}
	
	public void upload(String path, InputStream input) throws Exception {
		try {
			sendToFtp(path, input);
		} catch (Exception e) {
			throw e;
		}
	}
	
	private void sendToFtp(String path, InputStream bytes) throws Exception {
		try {
			if (path.isBlank()) {
				new InvalidParameterException("Diretório remoto não pode ser vazio");
			}
			ftpcliente.upload(path, bytes);
			
		} catch (Exception e) {
			throw e;
		}
	}
	
}
