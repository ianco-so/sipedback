package br.rn.sesed.sides.core.ftp;

import org.springframework.context.annotation.Configuration;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Configuration
class InboundConfiguration {

//	@Bean
//	public DefaultFtpSessionFactory defaultFtpSessionFactory() {
//		DefaultFtpSessionFactory defaultFtpSessionFactory = new DefaultFtpSessionFactory();
//		defaultFtpSessionFactory.setPassword("ffM?YZU[1J");
//		defaultFtpSessionFactory.setUsername("desaparecidos");
//		defaultFtpSessionFactory.setHost("10.9.192.43");
//		defaultFtpSessionFactory.setPort(21);
//		return defaultFtpSessionFactory;
//	}
//
//	@Bean
//	public IntegrationFlow inbound(DefaultFtpSessionFactory ftpSf) {
//		var localDirectory = new File(new File(System.getProperty("user.home"), "Desktop"), "local");
//		var spec = Ftp.inboundAdapter(ftpSf).autoCreateLocalDirectory(true).patternFilter("*.png").localDirectory(localDirectory);
//		return IntegrationFlows.from(spec, pc -> pc.poller(pm -> pm.fixedRate(1000, TimeUnit.MILLISECONDS))).handle((file, messageHeaders) -> {
//				log.info("new file: " + file + ".");
//				messageHeaders.forEach((k, v) -> log.info(k + ':' + v));
//				return null;
//			}).get();
//	}
}