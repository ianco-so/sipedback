package br.rn.sesed.sides.core.ftp;

import org.springframework.context.annotation.Configuration;


@Configuration
class IntegrationConfiguration {
//
//	@Bean
//	public ApacheMinaFtplet apacheMinaFtplet() {
//		return new ApacheMinaFtplet();
//	}
//
//	@Bean
//	public MessageChannel eventsChannel() {
//		return MessageChannels.direct().get();
//	}
//
//	@Bean
//	public IntegrationFlow integrationFlow() {
//		return IntegrationFlows.from(this.eventsChannel())
//			.handle((GenericHandler<ApacheMinaFtpEvent>) (apacheMinaFtpEvent, messageHeaders) -> {
//                //log.info("new event: " + apacheMinaFtpEvent.getClass().getName() + ':' + apacheMinaFtpEvent.getSession());
//				return null;
//			}).get();
//	}
}