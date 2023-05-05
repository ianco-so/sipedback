package br.rn.sesed.sides.core.ftp;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.stereotype.Component;

@Component
public class FtpClient {
	
	private final String server = "10.9.192.43";
	private final String username = "desaparecidos";
	private final String password = "ffM?YZU[1J";
	private final int port = 21; 

    private FTPClient ftpClient = new FTPClient();

    public void connect() throws IOException {
        ftpClient.connect(server, port);
        ftpClient.login(username, password);
        ftpClient.enterLocalPassiveMode();
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
    }
    
    public InputStream download(String remoteFilePath) throws IOException {
    	InputStream inputstream = ftpClient.retrieveFileStream(remoteFilePath);
    	return inputstream;
    }

    public boolean upload(String path, InputStream input) throws IOException {
        return ftpClient.storeFile(path, input);
    }

    public void disconnect() throws IOException {
        ftpClient.disconnect();
    }
}
