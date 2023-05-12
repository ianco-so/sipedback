package br.rn.sesed.sides.core.general;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class SidesConfig {

	@Bean
	ObjectMapper newObjectMapper() {
		return new ObjectMapper();
	}
}
