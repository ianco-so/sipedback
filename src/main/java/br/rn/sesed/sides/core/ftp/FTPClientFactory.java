package br.rn.sesed.sides.core.ftp;

import java.io.IOException;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.stereotype.Component;

import br.rn.sesed.sides.domain.exception.ErroAoConectarFtpException;

@Component
public class FTPClientFactory {
	 private final FTPProperties properties;

	    public FTPClientFactory(FTPProperties properties) {
	        this.properties = properties;
	    }

	    public FTPClient createClient() {
	        FTPClient ftpClient = new FTPClient();
	        try {
	            ftpClient.connect(properties.getHostname(), properties.getPort());
	            ftpClient.login(properties.getUsername(), properties.getPassword());
	            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
	            ftpClient.enterLocalPassiveMode();
	            ftpClient.pwd();
	        } catch (IOException e) {
	            throw new ErroAoConectarFtpException("Falha criando FTP client");
	        }
	        return ftpClient;
	    }
}
