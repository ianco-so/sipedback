package br.rn.sesed.sides.core.ftp;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;



@Component
public class FtpClientComponent {
	private static final Logger logger = LoggerFactory.getLogger(FtpClientComponent.class);
	
	private final String server = "10.9.192.43";
	private final String username = "desaparecidos";
	private final String password = "ffM?YZU[1J";
	private final int port = 21; 

    private FTPClient ftpClient = new FTPClient();

    public FTPClient connect() throws IOException {
        ftpClient.connect(server, port);
        ftpClient.login(username, password);
        ftpClient.enterLocalPassiveMode();
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
        return ftpClient;
    }
    
    public boolean upload(String path, InputStream input) throws IOException {
    	try {
    		FTPClient ftp = connect();
    		if(ftp.isConnected()) {
    			boolean ftpresult = ftp.storeFile(path, input);
    			ftp.disconnect();
    			return ftpresult;
    		}else {
    			new Exception("Não está conectada");
    			return false;
    		}
			
		} catch (Exception e) {
			throw e;
		}
    }
    
    public String[] listNames(String path) throws Exception {
    	try {
    		String[] pathname = ftpClient.listNames(path);
			return pathname;
		} catch (Exception e) {
			throw e;
		}
    }

    public void disconnect() throws IOException {
        ftpClient.disconnect();
    }
}
