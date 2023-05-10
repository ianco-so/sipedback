	package br.rn.sesed.sides.domain.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.stereotype.Service;

import br.rn.sesed.sides.core.ftp.FTPClientFactory;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FTPService {
	private final FTPClientFactory clientFactory;

	public FTPService(FTPClientFactory clientFactory) {
		this.clientFactory = clientFactory;
	}

	public void uploadFile(String remoteFilePath, File localFile) throws IOException {
		FTPClient ftpClient = clientFactory.createClient();
		ftpClient.changeWorkingDirectory(remoteFilePath);
		String destination = remoteFilePath;
		if (destination.endsWith("/")) {
	          destination += localFile.getName();
	        }
		try (InputStream inputStream = new FileInputStream(localFile)) {
			Boolean ret = ftpClient.storeFile(destination, inputStream);
			log.info("Salvo?: {} {}", ret, ftpClient.getReplyCode());
		} finally {
			ftpClient.disconnect();
		}
	}
	
	public Boolean uploadFile(String remoteFilePath, InputStream inputStream) throws IOException {
		FTPClient ftpClient = clientFactory.createClient();
		ftpClient.changeWorkingDirectory(remoteFilePath);
		String destination = remoteFilePath;
		try {
			if(inputStream.available() > 0) {
				Boolean ret = ftpClient.storeFile(destination, inputStream);
				log.info("Salvo?: {} {}", ret, ftpClient.getReplyCode());
				return ret;
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
    
}
