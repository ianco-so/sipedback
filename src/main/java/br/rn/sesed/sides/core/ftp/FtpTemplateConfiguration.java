package br.rn.sesed.sides.core.ftp;

import java.io.File;
import java.io.FileOutputStream;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.ftp.session.DefaultFtpSessionFactory;
import org.springframework.integration.ftp.session.FtpRemoteFileTemplate;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Configuration
class FtpTemplateConfiguration {

	@Bean
	DefaultFtpSessionFactory defaultFtpSessionFactory() {
		DefaultFtpSessionFactory defaultFtpSessionFactory = new DefaultFtpSessionFactory();
		defaultFtpSessionFactory.setPassword("ffM?YZU[1J");
		defaultFtpSessionFactory.setUsername("desaparecidos");
		defaultFtpSessionFactory.setHost("10.9.192.43");
		defaultFtpSessionFactory.setPort(21);
		return defaultFtpSessionFactory;
	}

	@Bean
	FtpRemoteFileTemplate ftpRemoteFileTemplate(DefaultFtpSessionFactory dsf) {
		return new FtpRemoteFileTemplate(dsf);
	}
	

	@Bean
	InitializingBean initializingBean(FtpRemoteFileTemplate template) {
		return () -> template
			.execute(session -> {
				var file = new File(new File(System.getProperty("user.home"), "Desktop"), "hello-local.txt");
				try (var fout = new FileOutputStream(file)) {
					session.read("hello.txt", fout);
				}
				log.info("read " + file.getAbsolutePath());
				return null;
			});
	}

}