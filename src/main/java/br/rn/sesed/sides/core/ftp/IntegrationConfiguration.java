package br.rn.sesed.sides.core.ftp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.ftp.server.ApacheMinaFtpEvent;
import org.springframework.integration.ftp.server.ApacheMinaFtplet;
import org.springframework.integration.handler.GenericHandler;
import org.springframework.messaging.MessageChannel;


@Configuration
class IntegrationConfiguration {

	@Bean
	public ApacheMinaFtplet apacheMinaFtplet() {
		return new ApacheMinaFtplet();
	}

	@Bean
	public MessageChannel eventsChannel() {
		return MessageChannels.direct().get();
	}

	@Bean
	public IntegrationFlow integrationFlow() {
		return IntegrationFlows.from(this.eventsChannel())
			.handle((GenericHandler<ApacheMinaFtpEvent>) (apacheMinaFtpEvent, messageHeaders) -> {
                //log.info("new event: " + apacheMinaFtpEvent.getClass().getName() + ':' + apacheMinaFtpEvent.getSession());
				return null;
			}).get();
	}

//	@Bean
//	public ApplicationEventListeningMessageProducer applicationEventListeningMessageProducer() {
//		ApplicationEventListeningMessageProducer producer = new ApplicationEventListeningMessageProducer();
//		producer.setEventTypes(ApacheMinaFtpEvent.class);
//		producer.setOutputChannel(eventsChannel());
//		return producer;
//	}
}