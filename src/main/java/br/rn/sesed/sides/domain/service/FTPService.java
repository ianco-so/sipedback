	package br.rn.sesed.sides.domain.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.rn.sesed.sides.core.ftp.FTPClientFactory;
import br.rn.sesed.sides.core.ftp.FTPProperties;
import br.rn.sesed.sides.domain.exception.ErroAoConectarFtpException;
import br.rn.sesed.sides.domain.exception.ErroAoSalvarFtpException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FTPService {
	private final FTPClientFactory clientFactory;
	@Autowired
	private FTPProperties properties;
	public FTPService(FTPClientFactory clientFactory) {
		this.clientFactory = clientFactory;
	}

	public void status() throws IOException {
		try {
			FTPClient ftpClient = clientFactory.createClient();
			if(!ftpClient.sendNoOp()) {
				throw new ErroAoConectarFtpException("Erro conectando FTP");
			}
		}catch (Exception e) {
			throw e;
		}
	}
	
	
	public Boolean uploadFile(String remoteFilePath, InputStream inputStream) throws IOException {
		FTPClient ftpClient = clientFactory.createClient();
		String destination = properties.getRemoteFilePath() + remoteFilePath;
		try {
			if(inputStream.available() > 0) {
				if(!ftpClient.storeFile(destination, inputStream)) {
					throw new ErroAoSalvarFtpException(ftpClient.getReplyString());
				}
				return true;
			}
		} finally {
			ftpClient.disconnect();
		}
		return false;
	}

	public void downloadFile(String remoteFilePath, File localFile) throws IOException {
		FTPClient ftpClient = clientFactory.createClient();
		ftpClient.changeWorkingDirectory(remoteFilePath);
		try (OutputStream outputStream = new FileOutputStream(localFile)) {
			ftpClient.retrieveFile(remoteFilePath, outputStream);
			
		} finally {
			ftpClient.disconnect();
		}
	}

	public void listDir(String directory) throws IOException {
    	FTPClient ftpClient = clientFactory.createClient();
    	ftpClient.changeWorkingDirectory(directory);
    	FTPFile[] files = ftpClient.listFiles();
    	for (FTPFile file : files) {
    		if (file.isFile()) {
    	}
    		System.out.println(file.getName());
    		
    	}
    	ftpClient.disconnect();
    	
    }
	
	public Boolean existDir(String directory) throws IOException {
    	FTPClient ftpClient = clientFactory.createClient();
    	ftpClient.changeWorkingDirectory(properties.getRemoteFilePath() + directory);
        if (ftpClient.getReplyCode() == 550) {
            return false;
        }      
    	ftpClient.disconnect();
		return true;
    	
    }
	
	public Boolean createDir(String directory) throws IOException {
    	FTPClient ftpClient = clientFactory.createClient();
    	ftpClient.mkd(properties.getRemoteFilePath() + directory);
        if (ftpClient.getReplyCode() == 550) {
            return false;
        }      
    	ftpClient.disconnect();
		return true;
    	
    }
    
}
