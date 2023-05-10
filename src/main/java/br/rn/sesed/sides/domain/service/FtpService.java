package br.rn.sesed.sides.domain.service;

import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.rn.sesed.sides.api.model.json.RegistroMultiPartJson;
import br.rn.sesed.sides.core.ftp.FtpClientComponent;

@Service
public class FtpService {

	@Autowired
	private FtpClientComponent ftpclientecomponent;
	
	public void upload(String path, RegistroMultiPartJson json) throws Exception {
		try {
			sendToFtp(path, json.getFotoPrincipal().getInputStream());
		} catch (Exception e) {
			throw e;
		}
	}
	
	public boolean upload(String path, InputStream input) throws Exception {
		try {
			return sendToFtp(path, input);
		} catch (Exception e) {
			throw e;
		}
	}
	
	private boolean sendToFtp(String path, InputStream bytes) throws Exception {
		try {
			return ftpclientecomponent.upload(path, bytes);
		} catch (Exception e) {
			throw e;
		}
	}
	
}
