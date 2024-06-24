package br.rn.sesed.sides.domain.desaparecidos.service;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.rn.sesed.sides.core.ftp.FTPClientFactory;
import br.rn.sesed.sides.core.ftp.FTPProperties;
import br.rn.sesed.sides.domain.desaparecidos.model.Foto;
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

	public void status() {
		try {
			FTPClient ftpClient = clientFactory.createClient();
			if (!ftpClient.sendNoOp()) {
				throw new ErroAoConectarFtpException("Erro conectando FTP");
			}
		} catch (IOException e) {
		}

	}

	public Boolean uploadFile(String remoteFilePath, InputStream inputStream) throws IOException {
		FTPClient ftpClient = clientFactory.createClient();
		String destination = properties.getRemoteFilePath() + remoteFilePath;
		try {
			if (inputStream.available() > 0) {
				if (!ftpClient.storeFile(destination, inputStream)) {
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

	public List<Foto> getFotosFromRegistroId(String remoteFilePath) throws IOException {
		List<Foto> fileList = new ArrayList<>();
		List<String> fileNames = new ArrayList<>();
		try {
			FTPClient ftpClient = clientFactory.createClient();
			FTPFile[] files = ftpClient.listFiles(remoteFilePath);
			for (FTPFile file : files) {
				fileNames.add(file.getName());
			}
			if (fileNames.size() > 0) {
				for (String filename : fileNames) {
					String encodedFile = "";
					InputStream is = ftpClient.retrieveFileStream(remoteFilePath + filename);
					File file = File.createTempFile("tmp", null);
					FileUtils.copyInputStreamToFile(is, file);
					byte[] bytes = new byte[(int) file.length()];
					BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
					bis.read(bytes, 0, (int) file.length());
					bis.close();
					encodedFile += new String(Base64.getEncoder().withoutPadding().encode(bytes), "UTF-8");
					fileList.add(new Foto(filename, encodedFile));
					file.delete();
					ftpClient.completePendingCommand();
				}
			}
			ftpClient.disconnect();
			return fileList;
		} catch (Exception e) {
			throw e;
		}
	}

	public byte[] downloadImage(String remoteFilePath) throws IOException {

		FTPClient ftpClient = clientFactory.createClient();
		byte[] imageBytes = null;

		try {

			ftpClient.enterLocalPassiveMode();

			try {



				InputStream inputStream = ftpClient.retrieveFileStream(remoteFilePath);
				ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

				byte[] buffer = new byte[4096];
				int bytesRead;
				while ((bytesRead = inputStream.read(buffer)) != -1) {
					outputStream.write(buffer, 0, bytesRead);
				}
				imageBytes = outputStream.toByteArray();
				if(!ftpClient.completePendingCommand()){
					throw new Exception("Comando não concluido. ");
				}

			} catch (Exception e) {
				throw new IOException(e.getMessage().concat(" Falha recuperando imagem"));
			}
		} finally {
			ftpClient.logout();
			ftpClient.disconnect();
		}

		return imageBytes;

	}


	public byte[] downloadImageWithoutExtension(String remoteFilePath, String remoteFileName) throws IOException {

		FTPClient ftpClient = clientFactory.createClient();
		byte[] imageBytes = null;
		String fileNameWithoutExtension = remoteFileName;
		try {

			ftpClient.enterLocalPassiveMode();

			try {

				// Lista os arquivos no diretório
				FTPFile[] files = ftpClient.listFiles(remoteFilePath);
				// Encontra o arquivo com o nome desejado (ignorando a extensão)
				String fullFileName = null;
				for (FTPFile file : files) {
					if (file.isFile() && file.getName().startsWith(fileNameWithoutExtension)) {
						fullFileName = file.getName();
						break;
					}
				}
				
				if (fullFileName == null) {
					System.out.println("Arquivo não encontrado.");
					throw new IOException(" Falha recuperando imagem");
				}

				InputStream inputStream = ftpClient.retrieveFileStream(remoteFilePath+"/"+fullFileName);
				ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

				byte[] buffer = new byte[4096];
				int bytesRead;
				while ((bytesRead = inputStream.read(buffer)) != -1) {
					outputStream.write(buffer, 0, bytesRead);
				}
				imageBytes = outputStream.toByteArray();
				if(!ftpClient.completePendingCommand()){
					throw new Exception("Comando não concluido. ");
				}

			} catch (Exception e) {
				throw new IOException(e.getMessage().concat(" Falha recuperando imagem"));
			}
		} finally {
			ftpClient.logout();
			ftpClient.disconnect();
		}

		return imageBytes;

	}

}
