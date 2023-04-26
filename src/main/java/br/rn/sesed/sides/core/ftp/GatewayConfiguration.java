package br.rn.sesed.sides.core.ftp;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("gateway")
class GatewayConfiguration {

//	@Bean
//	MessageChannel incoming() {
//		return MessageChannels.direct().get();
//	}
//
//	@Bean
//	IntegrationFlow gateway(
//		FtpRemoteFileTemplate template,
//		DelegatingSessionFactory<FTPFile> dsf) {
//		return f -> f
//			.channel(incoming())
//			.handle((GenericHandler<Object>) (key, messageHeaders) -> {
//				dsf.setThreadKey(key);
//				return key;
//			})
//			.handle(Ftp
//				.outboundGateway(template, AbstractRemoteFileOutboundGateway.Command.PUT, "payload")
//				.fileExistsMode(FileExistsMode.IGNORE)
//				.options(AbstractRemoteFileOutboundGateway.Option.RECURSIVE)
//			)
//			.handle((GenericHandler<Object>) (key, messageHeaders) -> {
//				dsf.clearThreadKey();
//				return null;
//			});
//	}
//
//	@Bean
//	DelegatingSessionFactory<FTPFile> dsf(Map<String, DefaultFtpSessionFactory> ftpSessionFactories) {
//		return new DelegatingSessionFactory<>(ftpSessionFactories::get);
//	}
//	
//    @Bean
//	FtpRemoteFileTemplate ftpRemoteFileTemplate(DelegatingSessionFactory<FTPFile> dsf) {
//		var ftpRemoteFileTemplate = new FtpRemoteFileTemplate(dsf);
//		ftpRemoteFileTemplate.setRemoteDirectoryExpression(new LiteralExpression(""));
//		return ftpRemoteFileTemplate;
//	}
}
