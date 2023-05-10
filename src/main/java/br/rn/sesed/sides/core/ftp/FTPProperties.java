package br.rn.sesed.sides.core.ftp;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "ftp")
public class FTPProperties {
	private String hostname;
    private int port;
    private String username;
    private String password;
}
